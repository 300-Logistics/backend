package com.example.hub.dto.response;

import java.util.UUID;

public record HubPathResponse(
    UUID hubPathId,
    UUID startHubId,
    UUID endHubId,
    Integer duration,
    Double distance
) {
}
