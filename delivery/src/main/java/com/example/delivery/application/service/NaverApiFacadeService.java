package com.example.delivery.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.delivery.application.dto.DirectionsResponseDto;
import com.example.delivery.application.dto.GeoCodingResponseDto;
import com.example.delivery.infrastructure.messaging.SlackMessagePublisher;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;
import com.example.delivery.presentation.dto.DirectionsRequestDto;
import com.example.delivery.presentation.dto.GeocodingRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverApiFacadeService {

	private final NaverDirectionsService naverDirectionsService;
	private final NaverGeoCodingService naverGeoCodingService;
	private final SlackMessagePublisher slackMessagePublisher;

	private String getCoordinatesFromAddress(String address) {
		GeoCodingResponseDto responseDto = naverGeoCodingService.getCoordinates(new GeocodingRequestDto(address));
		if (responseDto.addressInfoList().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_ADDRESS_EMPTY_OR_NULL);
		}
		GeoCodingResponseDto.AddressInfo info = responseDto.addressInfoList().get(0);
		return String.format("%s, %s", info.x(), info.y());
	}

	public DirectionsResponseDto getTrafastRoute(String startAddress, String goalAddress, List<String> waypointAddressList) {
		String startCoordinates = getCoordinatesFromAddress(startAddress);
		String goalCoordinates = getCoordinatesFromAddress(goalAddress);

		List<String> waypoints = getWaypoints(waypointAddressList);

		DirectionsRequestDto requestDto = new DirectionsRequestDto(startCoordinates, goalCoordinates, waypoints);
		return naverDirectionsService.getRoute(requestDto);
	}

	private List<String> getWaypoints(List<String> waypointAddressList) {
		if (waypointAddressList == null || waypointAddressList.isEmpty()) {
			return List.of();
		}
		return waypointAddressList.stream()
			.map(this::getCoordinatesFromAddress)
			.toList();
	}
}
