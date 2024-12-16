package com.example.auth.application.dto;

import java.util.UUID;

import com.example.auth.domain.model.enums.DeliveryType;

public record DeliveryStaffResponseDto(

	UUID id,
	String username,
	DeliveryType deliveryType,
	UUID hubId
) {
}
