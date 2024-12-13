package com.example.delivery.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.service.DeliveryService;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping
	public ResponseEntity<CreateDeliveryResponseDto> createDelivery(
		// @RequestHeader("X-User-Id") Long userId,
		// @RequestHeader("X-User-Role") String userRole,
		@RequestBody CreateDeliveryRequestDto requestDto
	) {
		CreateDeliveryResponseDto responseDto = deliveryService.createDelivery(
			// userId, userRole,
			requestDto
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
}
