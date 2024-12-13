package com.example.delivery.domain.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DeliveryStatusRecord;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "p_delivery")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private DeliveryStatusRecord deliveryStatusRecord;

	@Embedded
	private Address address;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Column(name = "deleted_by")
	private String deletedBy;

	private LocalDateTime deletedAt;

	@Column(nullable = false)
	private UUID hubDeliveryStaffId;

	@Column(nullable = false)
	private UUID companyDeliveryStaffId;

	@Column(nullable = false)
	private UUID receiverId;

	@Column(nullable = false)
	private UUID receiverSlackId;

	@Column(nullable = false)
	private UUID orderId;

	/**
	 * 허브 아이디 두개를 HubRoute 값객체로
	 * 바꿔야한다 -> 여기서 허브 경로 데이터를 관리해야함
	 * 안바꿔도 된다 -> 허브경로 정보를 여기서는 단순참조만 한다.
	 * 후자 선택
	 */
	private UUID startHubId;
	private UUID destinationHubId;
	private UUID hubDeliveryHistoryId;

	// TODO: 원투원 단방향에서 LAZY로딩 적용시 추가 쿼리 실행이 일어나기 때문에 비효율적.  변경이 필요하다면 추후 변경
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "company_delivery_history_id", referencedColumnName = "deliveryHistoryId")
	private CompanyDeliveryHistory companyDeliveryHistory;

	@OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeliveryStatusHistory> statusHistoryList = new ArrayList<>();

	@Builder
	private Delivery(DeliveryStatusRecord deliveryStatusRecord, Address address,
		UUID startHubId, UUID destinationHubId, UUID hubDeliveryStaffId, UUID companyDeliveryStaffId,
		UUID receiverId, UUID receiverSlackId, UUID orderId, UUID hubDeliveryHistoryId,
		CompanyDeliveryHistory companyDeliveryHistory) {
		this.deliveryStatusRecord = deliveryStatusRecord;
		this.address = address;
		this.startHubId = startHubId;
		this.destinationHubId = destinationHubId;
		this.hubDeliveryStaffId = hubDeliveryStaffId;
		this.companyDeliveryStaffId = companyDeliveryStaffId;
		this.receiverId = receiverId;
		this.receiverSlackId = receiverSlackId;
		this.orderId = orderId;
		this.hubDeliveryHistoryId = hubDeliveryHistoryId;
		this.companyDeliveryHistory = companyDeliveryHistory;
	}

	public static Delivery of(DeliveryStatusRecord deliveryStatusRecord, Address address,
		UUID startHubId, UUID destinationHubId, UUID hubDeliveryStaffId, UUID companyDeliveryStaffId,
		UUID receiverId, UUID receiverSlackId, UUID orderId, UUID hubDeliveryHistoryId,
		CompanyDeliveryHistory companyDeliveryHistory) {

		// TODO: 이후 api 구현시 검증로직 서비스로 이동
		validateParam(hubDeliveryStaffId, companyDeliveryStaffId, receiverId, receiverSlackId, orderId);

		Delivery delivery = Delivery.builder()
			.deliveryStatusRecord(deliveryStatusRecord)
			.address(address)
			.startHubId(startHubId)
			.destinationHubId(destinationHubId)
			.hubDeliveryStaffId(hubDeliveryStaffId)
			.companyDeliveryStaffId(companyDeliveryStaffId)
			.receiverId(receiverId)
			.receiverSlackId(receiverSlackId)
			.orderId(orderId)
			.hubDeliveryHistoryId(hubDeliveryHistoryId)
			.companyDeliveryHistory(companyDeliveryHistory)
			.build();

		delivery.statusHistoryList.add(
			DeliveryStatusHistory.of(deliveryStatusRecord.getDeliveryStatus(), delivery)
		);

		return delivery;
	}

	// TODO: 이후 api 구현시 검증로직 서비스로 이동
	private static void validateParam(UUID hubDeliveryStaffId, UUID companyDeliveryStaffId, UUID receiverId, UUID receiverSlackId, UUID orderId) {

		if (hubDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_HUB_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
		if (companyDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_COMPANY_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
		if (receiverId == null) {
			throw new CustomException(ErrorCode.INVALID_RECEIVER_ID_NULL);
		}
		if (receiverSlackId == null) {
			throw new CustomException(ErrorCode.INVALID_RECEIVER_SLACK_ID_NULL);
		}
		if (orderId == null) {
			throw new CustomException(ErrorCode.INVALID_ORDER_ID_NULL);
		}
	}

	public void update(Address address, UUID hubDeliveryStaffId, UUID companyDeliveryStaffId) {
		this.address = Optional.ofNullable(address).orElse(this.address);
		this.hubDeliveryStaffId = Optional.ofNullable(hubDeliveryStaffId).orElse(this.hubDeliveryStaffId);
		this.companyDeliveryStaffId = Optional.ofNullable(companyDeliveryStaffId).orElse(this.companyDeliveryStaffId);
	}

	public void updateDeliveryStatus(DeliveryStatus nextDeliveryStatus) {
		this.deliveryStatusRecord = this.deliveryStatusRecord.updateStatus(nextDeliveryStatus);
		this.statusHistoryList.add(DeliveryStatusHistory.of(nextDeliveryStatus, this));
	}

	public void setDeleted(String username) {
		this.isDeleted = true;
		this.deletedBy = username;
		this.deletedAt = LocalDateTime.now();
	}

	/**
	 * 업체배송경로 변경
	 * 기존 연관관계를 제거하고 새 연관관계를 맺어서
	 * 혹시 모를 잔여 데이터 제거
	 */
	public void setCompanyDeliveryHistory(CompanyDeliveryHistory companyDeliveryHistory) {
		// TODO: 이후 api 구현시 검증로직 서비스로 이동
		checkDeliveryStatus();

		removeExistingCompanyDelivery();

		if (companyDeliveryHistory != null) {
			this.companyDeliveryHistory = companyDeliveryHistory;
		}
	}

	// TODO: 이후 api 구현시 검증로직 서비스로 이동
	private void checkDeliveryStatus() {
		if (this.deliveryStatusRecord.getDeliveryStatus() != DeliveryStatus.ARRIVED_AT_DESTINATION_HUB &&
			this.deliveryStatusRecord.getDeliveryStatus() != DeliveryStatus.BEFORE_DELIVERY_START) {
			throw new CustomException(ErrorCode.INVALID_DELIVERY_STATUS);
		}
	}

	private void removeExistingCompanyDelivery() {
		if (this.companyDeliveryHistory != null) {
			this.companyDeliveryHistory = null;
		}
	}
}
