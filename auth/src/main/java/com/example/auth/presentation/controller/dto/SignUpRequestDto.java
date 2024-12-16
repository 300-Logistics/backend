package com.example.auth.presentation.controller.dto;

import com.example.auth.domain.model.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(
	@NotBlank
	@Size(max = 16, message = "최대 16자")
	String username,
	@NotBlank
	@Size(min = 4, max = 16, message = "최소 4자 최대 16자")
	String password,
	@NotBlank
	@Email
	String slackId,
	@NotBlank
	UserRole userRole
) {
}
