package com.example.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.delivery.application.service.interfaces.DeliveryFacadeService;
import com.example.delivery.application.service.interfaces.DeliveryService;
import com.example.delivery.domain.model.entity.Delivery;
import com.example.delivery.infrastructure.messaging.DeliveryStatusPublisher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryFacadeServiceImpl implements DeliveryFacadeService {

	private final DeliveryService deliveryService;
	private final DeliveryStatusPublisher deliveryStatusPublisher;

	@Override
	@Transactional
	public void updateDeliveryStatusAndNotifyToSlack(UUID deliveryId) {
		Delivery delivery = deliveryService.updateDeliveryStatus(deliveryId);

		deliveryStatusPublisher.publish(delivery.getReceiverSlackId(), deliveryId,
			delivery.getDeliveryStatusRecord().getDeliveryStatus().name(),
			delivery.getDeliveryStatusRecord().getUpdatedAt());
	}

}
