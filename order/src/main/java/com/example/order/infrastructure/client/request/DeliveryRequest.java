package com.example.order.infrastructure.client.request;

import java.util.UUID;

public record DeliveryRequest(
        UUID orderId,
        UUID startHubId,
        UUID destinationHubId,
        UUID receiverId,
        String receiverSlackId,
        String address
) {
}
