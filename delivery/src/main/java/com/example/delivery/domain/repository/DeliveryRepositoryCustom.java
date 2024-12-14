package com.example.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.delivery.domain.model.entity.Delivery;

public interface DeliveryRepositoryCustom {

	Optional<Delivery> findByIdWithConditions(UUID deliveryId, UUID userId);

	Page<Delivery> findAllWithConditions(UUID userId, Boolean isDeleted, Boolean isCompleted, Pageable pageable);

}
