package com.example.slack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.slack.entity.Slack;

public interface SlackMessageRepository extends JpaRepository<Slack, UUID> {
	List<Slack> findByChannel(String channel);
}
