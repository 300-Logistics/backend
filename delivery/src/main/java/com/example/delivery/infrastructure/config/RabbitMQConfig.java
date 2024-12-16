package com.example.delivery.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String DELIVERY_STATUS_ROUTING_KEY = "delivery.status";
	public static final String DELIVERY_STATUS_QUEUE = "delivery.status.queue";
	public static final String EXCHANGE = "delivery.status.exchange";

	public static final String SLACK_EXCHANGE = "slack.exchange";
	public static final String SLACK_ROUTE_NOTIFICATION_QUEUE = "slack.route.notification.queue";
	public static final String SLACK_ROUTE_NOTIFICATION_ROUTING_KEY = "slack.route.notification";


	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jackson2JsonMessageConverter());
		return template;
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jackson2JsonMessageConverter());
		return factory;
	}

	@Bean
	public TopicExchange deliveryExchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Queue deliveryStatusQueue() {
		return new Queue(DELIVERY_STATUS_QUEUE, true);
	}

	@Bean
	public Binding deliveryStatusBinding(Queue deliveryStatusQueue, TopicExchange deliveryExchange) {
		return BindingBuilder
			.bind(deliveryStatusQueue)
			.to(deliveryExchange)
			.with(DELIVERY_STATUS_ROUTING_KEY);
	}

	@Bean
	public DirectExchange slackExchange() {
		return new DirectExchange(SLACK_EXCHANGE);
	}

	@Bean
	public Queue slackRouteNotificationQueue() {
		return new Queue(SLACK_ROUTE_NOTIFICATION_QUEUE, true);
	}

	@Bean
	public Binding slackRouteNotificationBinding(Queue slackRouteNotificationQueue, DirectExchange slackExchange) {
		return BindingBuilder.bind(slackRouteNotificationQueue)
			.to(slackExchange)
			.with(SLACK_ROUTE_NOTIFICATION_ROUTING_KEY);
	}
}
