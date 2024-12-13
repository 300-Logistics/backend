package com.example.delivery.domain.model.entity;

import java.util.UUID;

import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "p_company_delivery_history")
public class CompanyDeliveryHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID deliveryHistoryId;

	@Embedded
	private Address address;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "distance", column = @Column(name = "expected_distance", nullable = false)),
		@AttributeOverride(name = "duration", column = @Column(name = "expected_duration", nullable = false))
	})
	private DistanceAndDuration expectedDistanceAndDuration;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "distance", column = @Column(name = "distance")),
		@AttributeOverride(name = "duration", column = @Column(name = "duration"))
	})
	private DistanceAndDuration distanceAndDuration;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Column(nullable = false)
	private UUID companyDeliveryStaffId;

	@Builder
	private CompanyDeliveryHistory(Address address, DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration, UUID companyDeliveryStaffId) {
		this.address = address;
		this.expectedDistanceAndDuration = expectedDistanceAndDuration;
		this.distanceAndDuration = distanceAndDuration;
		this.companyDeliveryStaffId = companyDeliveryStaffId;
	}

	public static CompanyDeliveryHistory of(Address address, DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration, UUID companyDeliveryStaffId) {

		// TODO: 이후 api 구현시 검증로직 서비스로 이동
		validateParam(expectedDistanceAndDuration, companyDeliveryStaffId);

		return CompanyDeliveryHistory.builder()
			.address(address)
			.expectedDistanceAndDuration(expectedDistanceAndDuration)
			.distanceAndDuration(distanceAndDuration)
			.companyDeliveryStaffId(companyDeliveryStaffId)
			.build();
	}

	// TODO: 이후 api 구현시 검증로직 서비스로 이동
	private static void validateParam(DistanceAndDuration expectedDistanceAndDuration, UUID companyDeliveryStaffId) {
		if (expectedDistanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
		if (companyDeliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_COMPANY_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
	}

	public void setDeleted(String username) {
		this.isDeleted = true;
	}
}
