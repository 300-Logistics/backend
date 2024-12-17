package com.example.auth.presentation.dto;

import java.util.UUID;

import com.example.auth.domain.model.enums.DeliveryType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryStaffRegisterRequestDto(
	@NotBlank
	String username,
	@NotNull
	DeliveryType deliveryType,
	@Email
	String slackId,
	UUID hubId

) {
}
