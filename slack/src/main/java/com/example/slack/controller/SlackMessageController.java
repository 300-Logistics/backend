package com.example.slack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.slack.entity.Slack;
import com.example.slack.service.SlackLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slack")
public class SlackMessageController {

	private final SlackLogService slackLogService;

	@GetMapping
	public List<Slack> getSlackMessage(@RequestParam String channel) {
		return slackLogService.getSlackMessage(channel);
	}
}
