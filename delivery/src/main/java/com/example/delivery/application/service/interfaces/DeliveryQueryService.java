package com.example.delivery.application.service.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.delivery.application.dto.GetDeliveryInfoResponseDto;
import com.example.delivery.application.dto.GetDeliveryListResponseDto;

public interface DeliveryQueryService {

	GetDeliveryInfoResponseDto getDeliveryInfo(UUID deliveryId, UUID userId, String userRole);

	Page<GetDeliveryListResponseDto> getDeliveryList(UUID userId, Boolean isDeleted, Boolean isCompleted, Pageable pageable);

}
