package com.example.hub.presentation.dto.request;

public record HubRequest(
    String name,
    String address,
    Double latitude,
    Double longitude
) {
}
