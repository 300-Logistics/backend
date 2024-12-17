package com.example.slack.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackApiClient {

	private final Slack slackClient;

	@Value("${slack.bot.token}")
	private String slackToken;

	public ChatPostMessageResponse sendSlackMessage(String channel, String text, String username) {
		try {
			return slackClient.methods(slackToken)
				.chatPostMessage(ChatPostMessageRequest.builder()
					.channel(channel)
					.text(text)
					.username(username)
					.build());
		} catch (SlackApiException | IOException  e) {
			log.error("슬랙 호출 예외 발생", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
