package com.example.delivery.infrastructure.messaging;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.delivery.infrastructure.config.RabbitMQConfig;
import com.example.delivery.infrastructure.event.SlackRouteNotificationMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackMessagePublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publishRouteNotification(UUID deliveryId, UUID deliveryStaffId, String startAddress, String goalAddress, double distance, int duration) {
		SlackRouteNotificationMessage message = new SlackRouteNotificationMessage(
			deliveryId, deliveryStaffId, startAddress, goalAddress, distance, duration);

		rabbitTemplate.convertAndSend(RabbitMQConfig.SLACK_EXCHANGE, RabbitMQConfig.SLACK_ROUTE_NOTIFICATION_ROUTING_KEY, message);
	}
}
