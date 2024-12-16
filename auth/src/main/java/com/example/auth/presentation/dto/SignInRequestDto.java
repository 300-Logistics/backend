package com.example.auth.presentation.dto;

public record SignInRequestDto(
	String username,
	String password
) {
}
