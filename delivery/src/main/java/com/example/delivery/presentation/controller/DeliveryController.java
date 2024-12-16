package com.example.delivery.presentation.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.dto.CreateDeliveryResponseDto;
import com.example.delivery.application.service.interfaces.DeliveryService;
import com.example.delivery.application.service.interfaces.DeliveryToSlackFacadeService;
import com.example.delivery.presentation.dto.CreateDeliveryRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

	private final DeliveryService deliveryService;
	private final DeliveryToSlackFacadeService deliveryFacadeService;

	@PostMapping
	public ResponseEntity<CreateDeliveryResponseDto> createDelivery(
		@RequestHeader("X-User-Id") UUID userId,
		@RequestHeader("X-User-Role") String userRole,
		@RequestBody CreateDeliveryRequestDto requestDto
	) {
		CreateDeliveryResponseDto responseDto = deliveryService.createDelivery(
			requestDto, userId, userRole);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	// @PatchMapping("/{deliveryId}")
	// public ResponseEntity<UpdateDeliveryResponseDto> updateDelivery(
	// 	@RequestHeader("X-User-Id") UUID userId,
	// 	@RequestHeader("X-User-Role") String userRole,
	// 	@PathVariable UUID deliveryId,
	// 	@RequestBody UpdateDeliveryRequestDto requestDto
	// ) {
	// 	UpdateDeliveryResponseDto responseDto = deliveryService.updateDelivery(
	// 		deliveryId, requestDto, userId, userRole);
	// 	return ResponseEntity.ok(responseDto);
	// }

	@PatchMapping("/{deliveryId}/status")
	public ResponseEntity<Void> updateDeliveryStatus(
		@RequestHeader("X-User-Id") UUID userId,
		@RequestHeader("X-User-Role") String userRole,
		@PathVariable UUID deliveryId
	) {
		deliveryFacadeService.updateDeliveryStatusAndNotifyToSlack(deliveryId, userId, userRole);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{deliveryId}")
	public ResponseEntity<Void> cancelDelivery(
		@RequestHeader("X-User-Id") UUID userId,
		@RequestHeader("X-User-Role") String userRole,
		@PathVariable UUID deliveryId
	) {
		deliveryService.cancelDelivery(deliveryId, userId, userRole);
		return ResponseEntity.noContent().build();
	}
}
