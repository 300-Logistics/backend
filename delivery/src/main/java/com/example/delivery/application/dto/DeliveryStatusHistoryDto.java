package com.example.delivery.application.dto;

import java.time.LocalDateTime;

public record DeliveryStatusHistoryDto(

	String status,
	LocalDateTime updatedAt

) {
}
