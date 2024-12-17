package com.example.slack.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slack")
public class Slack {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String channel;
	private String username;

	@Column(columnDefinition = "TEXT")
	private String messageContent;

	private boolean isSuccess;

	private String errorLog;

	private LocalDateTime sendAt;

	@Builder
	public Slack(String channel, String username, String messageContent, boolean isSuccess, String errorLog, LocalDateTime sendAt) {
		this.channel = channel;
		this.username = username;
		this.messageContent = messageContent;
		this.sendAt = sendAt;
	}

	public void setSuccess() {
		this.isSuccess = true;
	}

	public void setFail(String errorLog) {
		this.isSuccess = false;
		this.errorLog = errorLog;
	}
}
