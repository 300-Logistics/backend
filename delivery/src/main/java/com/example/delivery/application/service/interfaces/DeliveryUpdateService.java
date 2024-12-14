package com.example.delivery.application.service.interfaces;

import java.util.UUID;

import com.example.delivery.application.dto.UpdateDeliveryResponseDto;
import com.example.delivery.presentation.dto.UpdateDeliveryRequestDto;

public interface DeliveryUpdateService {

	UpdateDeliveryResponseDto updateDelivery(UUID deliveryId, UpdateDeliveryRequestDto requestDto);

}
