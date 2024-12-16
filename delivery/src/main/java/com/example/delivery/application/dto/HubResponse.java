package com.example.delivery.application.dto;

import java.util.UUID;

public record HubResponse(
	UUID id,
	String name,
	String address,
	Double latitude,
	Double longitude
) {
}