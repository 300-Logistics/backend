package com.example.hub.dto.request;

import java.util.UUID;

public record HubPathRequest(
    UUID startHubId,
    UUID endHubId
) {
}
