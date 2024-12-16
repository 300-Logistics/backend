package com.example.delivery.application.service.interfaces;

import java.util.UUID;

import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

public interface DeliveryToSlackFacadeService {

	void updateDeliveryStatusAndNotifyToSlack(UUID deliveryId, UUID userId, String userRole);

	void createDeliveryAndNotifyToSlack(CreateDeliveryRequestDto requestDto, UUID userId, String userRole);

}
