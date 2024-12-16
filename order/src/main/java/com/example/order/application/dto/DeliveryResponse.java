package com.example.order.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponse(
        UUID deliveryId,
        UUID hubDeliveryStaffId,
        UUID companyDeliveryStaffId,
        UUID startHubId,
        UUID destinationHubId,
        UUID receiverId,
        UUID receiverSlackId,
        String address,
        String status,
        LocalDateTime updatedAt,
        double expectedDistance,
        int expectedDuration
) {
}
