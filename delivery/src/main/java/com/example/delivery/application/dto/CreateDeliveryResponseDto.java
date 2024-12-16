package com.example.delivery.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateDeliveryResponseDto(

	UUID deliveryId,
	UUID hubDeliveryStaffId,
	UUID companyDeliveryStaffId,
	UUID startHubId,
	UUID destinationHubId,
	String destinationHubAddress,
	UUID receiverId,
	String receiverSlackId,
	String address,
	String status,
	LocalDateTime updatedAt,
	double expectedDistance,
	int expectedDuration

) {
}
