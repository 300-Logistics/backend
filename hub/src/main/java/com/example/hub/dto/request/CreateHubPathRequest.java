package com.example.hub.dto.request;

import java.util.UUID;

public record CreateHubPathRequest(
    UUID startHubId,
    UUID endHubId,
    Integer duration,
    Double distance
) {
}
