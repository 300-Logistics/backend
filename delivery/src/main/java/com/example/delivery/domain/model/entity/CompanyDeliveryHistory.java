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
		@AttributeOverride(name = "distance", column = @Column(name = "distance", nullable = false)),
		@AttributeOverride(name = "duration", column = @Column(name = "duration", nullable = false))
	})
	private DistanceAndDuration distanceAndDuration;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Column(nullable = false)
	private UUID deliveryStaffId;

	@Builder
	private CompanyDeliveryHistory(Address address, DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration, UUID deliveryStaffId) {
		this.address = address;
		this.expectedDistanceAndDuration = expectedDistanceAndDuration;
		this.distanceAndDuration = distanceAndDuration;
		this.deliveryStaffId = deliveryStaffId;
	}

	public static CompanyDeliveryHistory of(Address address, DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration, UUID deliveryStaffId) {

		validateParam(expectedDistanceAndDuration, distanceAndDuration, deliveryStaffId);

		return CompanyDeliveryHistory.builder()
			.address(address)
			.expectedDistanceAndDuration(expectedDistanceAndDuration)
			.distanceAndDuration(distanceAndDuration)
			.deliveryStaffId(deliveryStaffId)
			.build();
	}

	private static void validateParam(DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration,
		UUID deliveryStaffId) {
		if (expectedDistanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
		if (distanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
		if (deliveryStaffId == null) {
			throw new CustomException(ErrorCode.INVALID_DELIVERY_STAFF_EMPTY_OR_NULL);
		}
	}

	public void setDeleted(String username) {
		this.isDeleted = true;
	}
}
