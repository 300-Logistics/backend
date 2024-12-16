package com.example.delivery.presentation.dto;

public record GetDeliveryListRequestDto(
	Boolean isDeleted,
	Boolean isCompleted
) {
}
