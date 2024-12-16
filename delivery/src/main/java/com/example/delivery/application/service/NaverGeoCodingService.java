package com.example.delivery.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.delivery.application.dto.GeoCodingResponseDto;
import com.example.delivery.presentation.dto.GeocodingRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverGeoCodingService {

	private final WebClient naverGeoCodingWebClient;

	public GeoCodingResponseDto getCoordinates(GeocodingRequestDto requestDto) {
		return naverGeoCodingWebClient.get()
			.uri(uriBuilder -> uriBuilder.queryParam("query", requestDto.address()).build())
			.retrieve()
			.bodyToMono(GeoCodingResponseDto.class)
			.block();
	}
}
