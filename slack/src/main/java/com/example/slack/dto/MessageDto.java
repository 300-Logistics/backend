package com.example.slack.dto;

import java.util.List;

public record MessageDto(
	String channel,
	String username,
	MessageContent messageContent

) {
	public record MessageContent(
		String title,
		String description,
		List<DetailContent> detailContentList
	) {
		public record DetailContent(
			String key,
			String value
		) {
		}

		public String formatMessage() {
			StringBuilder sb = new StringBuilder();
			sb.append("*").append(title).append("*\n\n");
			sb.append(description).append("\n\n");

			detailContentList.forEach(detail ->
				sb.append(" *").append(detail.key).append(" : ").append(detail.value).append("\n"));
			return sb.toString();
		}
	}

}
