package com.example.slack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.slack.entity.Slack;
import com.example.slack.repository.SlackMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackLogService {

	private final SlackMessageRepository slackMessageRepository;

	public List<Slack> getSlackMessage(String channel) {
		if (channel == null || channel.isEmpty()) {
			return slackMessageRepository.findAll();
		}
		return slackMessageRepository.findByChannel(channel);
	}
}
