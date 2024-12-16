package com.example.delivery.presentation.dto;

import java.util.List;

public record DirectionsRequestDto(
	String start,
	String goal,
	List<String> waypoints
) {
}
