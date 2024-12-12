package com.example.hub.dto.request;

public record HubRequest(
    String name,
    String address,
    Double latitude,
    Double longitude
) {
}
