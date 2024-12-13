package com.example.delivery.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateDeliveryResponseDto(

	UUID deliveryId,
	String address,
	String status,
	LocalDateTime updatedAt

) {
}
