package com.example.delivery.application.service;

import java.util.UUID;

import com.example.delivery.domain.model.entity.Delivery;

public interface DeliveryStatusUpdateService {

	Delivery updateDeliveryStatus(UUID deliveryId);

}
