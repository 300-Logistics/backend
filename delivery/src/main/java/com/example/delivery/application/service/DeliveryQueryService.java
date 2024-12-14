package com.example.delivery.application.service;

import java.util.UUID;

import com.example.delivery.domain.model.entity.Delivery;

public interface DeliveryQueryService {

	Delivery findDeliveryById(UUID deliveryId);

	// Page<Delivery> findDeliveryList(UUID id, DeliveryQueryType type, Pageable pageable);

}
