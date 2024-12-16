package com.example.delivery.infrastructure.event;

import java.util.UUID;

public record UpdateAddressMessage(
	UUID deliveryId,
	String updatedAddress
) {
}
