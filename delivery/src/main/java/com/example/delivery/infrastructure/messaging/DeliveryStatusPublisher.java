package com.example.delivery.infrastructure.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.delivery.infrastructure.config.RabbitMQConfig;
import com.example.delivery.infrastructure.event.DeliveryStatusMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryStatusPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publish(UUID deliveryId, String updatedStatus, LocalDateTime updatedAt) {
		DeliveryStatusMessage message = new DeliveryStatusMessage(deliveryId, updatedStatus, updatedAt);

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELIVERY_STATUS_ROUTING_KEY, message);
	}
}
