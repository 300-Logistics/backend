package com.example.delivery.application.service.interfaces;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

public interface DeliveryCreateService {

	CreateDeliveryResponseDto createDelivery(CreateDeliveryRequestDto requestDto);

}
