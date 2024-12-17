package com.example.slack.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.slack.dto.MessageDto;
import com.example.slack.event.DeliveryStatusMessage;
import com.example.slack.service.SlackMessageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryStatusListener {

	private final SlackMessageService slackMessageService;

	@RabbitListener(queues = "delivery.status.queue")
	public void receiveDeliveryStatus(DeliveryStatusMessage message) {
		MessageDto messageDto = new MessageDto(
			message.receiverSlackId(),
			"DeliveryBot",
			new MessageDto.MessageContent(
				"배송 상태 알림",
				"배송 상태가 변경되었습니다.",
				List.of(
					new MessageDto.MessageContent.DetailContent("배송 ID", message.deliveryId().toString()),
					new MessageDto.MessageContent.DetailContent("배송 상태", message.updatedStatus()),
					new MessageDto.MessageContent.DetailContent("업데이트 시간", message.updatedAt().toString())
				)
			)
		);

		slackMessageService.sendFormattedMessage(messageDto);
	}
}
