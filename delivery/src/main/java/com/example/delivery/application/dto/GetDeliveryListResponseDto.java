package com.example.delivery.application.dto;

import java.util.UUID;

public record GetDeliveryListResponseDto(
	UUID deliveryId,
	boolean isCompleted,
	String deliveryStatus

) {
}
