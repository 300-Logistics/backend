package com.example.delivery.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.delivery.application.dto.DeliveryStatusHistoryDto;
import com.example.delivery.application.dto.GetDeliveryInfoResponseDto;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.domain.repository.DeliveryRepository;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryQueryServiceImpl implements DeliveryQueryService{

	private final DeliveryRepository deliveryRepository;

	@Override
	public GetDeliveryInfoResponseDto getDeliveryInfo(UUID deliveryId, UUID userId, String userRole) {
		Delivery delivery;

		if ("MASTER".equals(userRole)) {
			delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
		} else {
			delivery = deliveryRepository.findByIdWithConditions(deliveryId, userId)
				.orElseThrow(() -> new CustomException(ErrorCode.DELIVERY_NOT_FOUND));
		}

		List<DeliveryStatusHistoryDto> statusHistoryDtoList = delivery.getStatusHistoryList().stream()
			.map(deliveryStatusHistory -> new DeliveryStatusHistoryDto(
				deliveryStatusHistory.getDeliveryStatus().name(),
				deliveryStatusHistory.getUpdatedAt()
			))
			.toList();

		return new GetDeliveryInfoResponseDto(
			deliveryId,
			delivery.getHubDeliveryStaffId(),
			delivery.getCompanyDeliveryStaffId(),
			delivery.getReceiverId(),
			delivery.getAddress().getFullAddress(),
			statusHistoryDtoList
		);
	}
}
