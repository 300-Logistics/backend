package com.example.auth.presentation.controller.dto;

public record SignInRequestDto(
	String username,
	String password
) {
}
