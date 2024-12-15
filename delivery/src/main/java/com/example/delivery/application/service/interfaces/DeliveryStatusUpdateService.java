package com.example.delivery.application.service.interfaces;

import java.util.UUID;

import com.example.delivery.domain.model.entity.Delivery;

public interface DeliveryStatusUpdateService {

	Delivery updateDeliveryStatus(UUID deliveryId, UUID userId, String userRole);

}
