package com.example.delivery.domain.model.vo;

import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HubRoute {

	@Column(nullable = false)
	private UUID startHubId;

	@Column(nullable = false)
	private UUID destinationHubId;

	public static HubRoute of(UUID startHubId, UUID destinationHubId) {
		validateHubId(startHubId, destinationHubId);
		validateHubIdsNotEquals(startHubId, destinationHubId);
		return new HubRoute(startHubId, destinationHubId);
	}

	private static void validateHubIdsNotEquals(UUID startHubId, UUID destinationHubId) {
		if (Objects.equals(startHubId, destinationHubId)) {
			throw new CustomException(ErrorCode.INVALID_HUB_ID_EQUALS);
		}
	}

	private static void validateHubId(UUID startHubId, UUID destinationHubId) {
		if (startHubId == null || destinationHubId == null) {
			throw new CustomException(ErrorCode.INVALID_HUB_ID_NULL);
		}
	}
}
