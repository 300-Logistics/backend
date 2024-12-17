package com.example.slack.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryStatusMessage(
	String receiverSlackId,
	UUID deliveryId,
	String updatedStatus,
	LocalDateTime updatedAt

) {
}
