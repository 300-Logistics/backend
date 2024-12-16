package com.example.delivery.domain.model.entity;

import java.util.UUID;

import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.domain.model.vo.HubRoute;

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
@Table(name = "p_hub_delivery_history")
public class HubDeliveryHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID hubDeliveryHistoryId;

	@Embedded
	private HubRoute hubRoute;

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

	@Builder
	private HubDeliveryHistory(HubRoute hubRoute,
		DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration) {
		this.hubRoute = hubRoute;
		this.expectedDistanceAndDuration = expectedDistanceAndDuration;
		this.distanceAndDuration = distanceAndDuration;
	}

	public static HubDeliveryHistory of(HubRoute hubRoute,
		DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration) {

		return HubDeliveryHistory.builder()
			.hubRoute(hubRoute)
			.expectedDistanceAndDuration(expectedDistanceAndDuration)
			.distanceAndDuration(distanceAndDuration)
			.build();
	}

	public void setDeleted(String username) {
		this.isDeleted = true;
	}

}
