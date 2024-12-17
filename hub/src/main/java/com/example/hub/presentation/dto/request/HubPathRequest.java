package com.example.hub.presentation.dto.request;

import java.util.UUID;

public record HubPathRequest(
    UUID startHubId,
    UUID endHubId
) {
}
