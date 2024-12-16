package com.example.auth.presentation.controller.dto;

import com.example.auth.domain.model.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequestDto(
	@NotBlank
	@Pattern(regexp = "^[a-z0-9]{4,10}$", message = "소문자 a~z  숫자 0~9  최소 4자 최대 10자")
	String username,
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]{8,15}$",
	message = "대소문자 숫자 특수문자  최소 8자 최대 15자")
	String password,
	@NotBlank
	@Email
	String slackId,
	@NotBlank
	UserRole userRole
) {
}
