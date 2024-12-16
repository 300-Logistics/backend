package com.example.delivery.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateDeliveryStatusResponseDto(
	UUID deliveryId,
	String status,
	LocalDateTime updatedAt

) {
}
