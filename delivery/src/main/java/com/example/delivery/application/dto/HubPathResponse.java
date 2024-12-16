package com.example.delivery.application.dto;

import java.util.UUID;

public record HubPathResponse(
	UUID hubPathId,
	UUID startHubId,
	UUID endHubId,
	Integer duration,
	Double distance
) {
}