package com.example.delivery.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.delivery.application.dto.DirectionsResponseDto;
import com.example.delivery.presentation.dto.DirectionsRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverDirectionsService {

	private final WebClient naverApiWebClient;

	public DirectionsResponseDto getRoute(DirectionsRequestDto requestDto) {
		return naverApiWebClient.get()
			.uri(uriBuilder -> uriBuilder
				.queryParam("start", requestDto.start())
				.queryParam("goal", requestDto.goal())
				.queryParam("waypoints", requestDto.waypoints())
				.queryParam("option", "trafast")
				.build()
			)
			.retrieve()
			.bodyToMono(DirectionsResponseDto.class)
			.block();
	}
}
