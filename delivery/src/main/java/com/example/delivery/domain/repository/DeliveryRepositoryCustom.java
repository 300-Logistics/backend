package com.example.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.delivery.domain.model.entity.Delivery;

public interface DeliveryRepositoryCustom {

	Optional<Delivery> findByIdWithConditions(UUID deliveryId, UUID userId);
}
