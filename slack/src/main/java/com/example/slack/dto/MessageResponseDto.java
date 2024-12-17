package com.example.slack.dto;

public record MessageResponseDto(
	boolean success,
	String message,
	String error
) {
}
