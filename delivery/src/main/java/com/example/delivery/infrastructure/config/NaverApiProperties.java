package com.example.delivery.infrastructure.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "naver.api")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverApiProperties {

	private ApiConfig geoCoding;
	private ApiConfig direction;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ApiConfig {
		private String url;
		private Map<String, String> headers;

	}
}
