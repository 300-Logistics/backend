package com.example.hub.presentation.dto.response;

import java.util.UUID;

public record HubResponse(
    UUID id,
    String name,
    String address,
    Double latitude,
    Double longitude
) {
}
