package com.example.slack.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.slack.dto.MessageDto;
import com.example.slack.event.SlackRouteNotificationMessage;
import com.example.slack.service.SlackMessageService;
import com.example.slack.service.SlackUserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RouteNotificationListener {

	private final SlackMessageService slackMessageService;
	private final SlackUserService slackUserService;

	@RabbitListener(queues = "slack.route.notification.queue")
	public void receiverRouteNotification(SlackRouteNotificationMessage message) {
		String slackId = slackUserService.findSlackId(message.deliveryStaffId())
			.orElseGet(() -> "develop");
		MessageDto messageDto = new MessageDto(
			slackId,
			"RouteBot",
			new MessageDto.MessageContent(
				"배송 경로 안내",
				"새 배송 경로가 할당되었습니다.",
				List.of(
					new MessageDto.MessageContent.DetailContent("배송 ID", message.deliveryId().toString()),
					new MessageDto.MessageContent.DetailContent("출발지", message.startAddress()),
					new MessageDto.MessageContent.DetailContent("도착지", message.goalAddress()),
					new MessageDto.MessageContent.DetailContent("거리", String.valueOf(message.distance())),
					new MessageDto.MessageContent.DetailContent("소요 시간", String.valueOf(message.duration()))
				)
			)
		);

		slackMessageService.sendFormattedMessage(messageDto);
	}
}
