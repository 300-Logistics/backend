package com.example.slack.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.slack.client.SlackApiClient;
import com.example.slack.dto.MessageDto;
import com.example.slack.dto.MessageResponseDto;
import com.example.slack.entity.Slack;
import com.example.slack.repository.SlackMessageRepository;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackMessageService {

	private final SlackApiClient slackApiClient;
	private final SlackMessageRepository slackMessageRepository;

	public MessageResponseDto sendFormattedMessage(MessageDto messageDto) {

		String formattedMessage = messageDto.messageContent().formatMessage();

		Slack slack = Slack.builder()
			.channel(messageDto.channel())
			.username(messageDto.username())
			.messageContent(formattedMessage)
			.sendAt(LocalDateTime.now())
			.build();

		try {
			ChatPostMessageResponse response = slackApiClient.sendSlackMessage(
				messageDto.channel(),
				formattedMessage,
				messageDto.username()
			);

			if (response.isOk()) {
				slack.setSuccess();
				slackMessageRepository.save(slack);
				return new MessageResponseDto(true, formattedMessage, null);
			} else {
				slack.setFail(response.getError());
				slackMessageRepository.save(slack);
				log.error("전송 실패 {}", response.getError());
				return new MessageResponseDto(false, formattedMessage, response.getError());
			}
		} catch (Exception e) {
			slack.setFail(e.getMessage());
			slackMessageRepository.save(slack);
			log.error("전송 중 실패", e);
			return new MessageResponseDto(false, formattedMessage, e.getMessage());
		}
	}
}
