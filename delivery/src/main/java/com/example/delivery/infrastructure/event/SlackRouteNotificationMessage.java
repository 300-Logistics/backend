package com.example.delivery.infrastructure.event;

import java.util.UUID;

public record SlackRouteNotificationMessage(
	UUID deliveryId,
	UUID deliveryStaffId,
	String startAddress,
	String goalAddress,
	double distance,
	int duration

) {
}
