package com.example.delivery.domain.model.entity;

import java.util.UUID;

import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DistanceAndDuration;

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

		return CompanyDeliveryHistory.builder()
			.address(address)
			.expectedDistanceAndDuration(expectedDistanceAndDuration)
			.distanceAndDuration(distanceAndDuration)
			.companyDeliveryStaffId(companyDeliveryStaffId)
			.build();
	}


	public void setDeleted(String username) {
		this.isDeleted = true;
	}
}
