package com.example.delivery.application.dto;

import java.util.UUID;

public record HubRouteResponseDto(
	UUID hubRouteId,
	int sequence,
	double distance,
	int duration

) {
}
