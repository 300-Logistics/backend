package com.example.delivery.infrastructure.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient naverApiWebClient(NaverApiProperties naverApiProperties) {
		return WebClient.builder()
			.baseUrl(naverApiProperties.getDirection().getUrl())
			.defaultHeaders(headers -> {
				Map<String, String> headerMap = naverApiProperties.getDirection().getHeaders();
				headerMap.forEach(headers::add);
			})
			.build();
	}

	@Bean
	WebClient naverGeoCodingWebClient(NaverApiProperties naverApiProperties) {
		return WebClient.builder()
			.baseUrl(naverApiProperties.getGeoCoding().getUrl())
			.defaultHeaders(headers -> {
				Map<String, String> headerMap = naverApiProperties.getGeoCoding().getHeaders();
				headerMap.forEach(headers::add);
			})
			.build();
	}
}
