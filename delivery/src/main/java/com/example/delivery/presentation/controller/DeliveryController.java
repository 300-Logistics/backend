package com.example.delivery.presentation.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.dto.UpdateDeliveryResponseDto;
import com.example.delivery.application.service.DeliveryService;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;
import com.example.delivery.presentation.dto.UpdateDeliveryRequestDto;

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

	@PatchMapping("/{deliveryId}")
	public ResponseEntity<UpdateDeliveryResponseDto> updateDelivery(
		// @RequestHeader("X-User-Id") Long userId,
		// @RequestHeader("X-User-Role") String userRole,
		@PathVariable UUID deliveryId,
		@RequestBody UpdateDeliveryRequestDto requestDto
	) {
		UpdateDeliveryResponseDto responseDto = deliveryService.updateDelivery(deliveryId,
		// userId, userRole,
			requestDto
		);
		return ResponseEntity.ok(responseDto);
	}
}
