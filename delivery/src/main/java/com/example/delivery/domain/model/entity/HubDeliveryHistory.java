package com.example.delivery.domain.model.entity;

import java.util.UUID;

import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.domain.model.vo.HubRoute;
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
@Table(name = "p_hub_delivery_history")
public class HubDeliveryHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID hubDeliveryHistoryId;

	@Embedded
	private HubRoute hubRoute;

	@Column(nullable = false)
	private int sequence;

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
	private HubDeliveryHistory(HubRoute hubRoute, int sequence,
		DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration) {
		this.hubRoute = hubRoute;
		this.sequence = sequence;
		this.expectedDistanceAndDuration = expectedDistanceAndDuration;
		this.distanceAndDuration = distanceAndDuration;
	}

	public static HubDeliveryHistory of(HubRoute hubRoute, int sequence,
		DistanceAndDuration expectedDistanceAndDuration,
		DistanceAndDuration distanceAndDuration) {

		// TODO: 이후 api 구현시 검증로직 서비스로 이동
		validateParam(sequence, expectedDistanceAndDuration);

		return HubDeliveryHistory.builder()
			.hubRoute(hubRoute)
			.sequence(sequence)
			.expectedDistanceAndDuration(expectedDistanceAndDuration)
			.distanceAndDuration(distanceAndDuration)
			.build();
	}

	// TODO: 이후 api 구현시 검증로직 서비스로 이동
	private static void validateParam(int sequence,
		DistanceAndDuration expectedDistanceAndDuration) {
		if (sequence < 0) {
			throw new CustomException(ErrorCode.INVALID_SEQUENCE_NOT_BELOW_ZERO);
		}
		if (expectedDistanceAndDuration == null) {
			throw new CustomException(ErrorCode.INVALID_DISTANCE_OR_DURATION_IS_NOT_NULL);
		}
	}

	public void setDeleted(String username) {
		this.isDeleted = true;
	}

}
