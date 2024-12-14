package com.example.delivery.application.service;

import java.util.UUID;

import com.example.delivery.application.dto.GetDeliveryInfoResponseDto;

public interface DeliveryQueryService {

	GetDeliveryInfoResponseDto getDeliveryInfo(UUID deliveryId, UUID userId, String userRole);

	// Page<Delivery> findDeliveryList(UUID id, DeliveryQueryType type, Pageable pageable);

}
