package com.example.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.service.interfaces.DeliveryService;
import com.example.delivery.application.service.interfaces.DeliveryToSlackFacadeService;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.infrastructure.messaging.DeliveryStatusPublisher;
import com.example.delivery.infrastructure.messaging.SlackMessagePublisher;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryToSlackFacadeServiceImpl implements DeliveryToSlackFacadeService {

	private final DeliveryService deliveryService;
	private final DeliveryStatusPublisher deliveryStatusPublisher;
	private final SlackMessagePublisher slackMessagePublisher;

	@Override
	@Transactional
	public void updateDeliveryStatusAndNotifyToSlack(UUID deliveryId, UUID userId, String userRole) {
		Delivery delivery = deliveryService.updateDeliveryStatus(deliveryId, userId, userRole);

		deliveryStatusPublisher.publish(
			delivery.getReceiverSlackId(),
			deliveryId,
			delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
			delivery.getDeliveryStatusRecord().getUpdatedAt());
	}

	@Override
	@Transactional
	public void createDeliveryAndNotifyToSlack(CreateDeliveryRequestDto requestDto, UUID userId, String userRole) {
		CreateDeliveryResponseDto delivery = deliveryService.createDelivery(requestDto, userId, userRole);

		slackMessagePublisher.publishRouteNotification(
			delivery.deliveryId(),
			delivery.companyDeliveryStaffId(),
			delivery.destinationHubAddress(),
			delivery.address(),
			delivery.expectedDistance(),
			delivery.expectedDuration()
		);
	}

}
