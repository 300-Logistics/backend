package com.example.delivery.application.dto;

import java.util.List;
import java.util.UUID;

public record GetDeliveryInfoResponseDto(

	UUID deliveryId,
	UUID hubDeliveryStaffId,
	UUID companyDeliveryStaffId,
	UUID receiverId,
	String address,
	boolean isCompleted,
	List<DeliveryStatusHistoryDto> statusHistoryDtoList

) {
}
