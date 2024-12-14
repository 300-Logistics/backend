package com.example.delivery.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.dto.GetDeliveryInfoResponseDto;
import com.example.delivery.application.service.DeliveryQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryQueryController {

	private final DeliveryQueryService deliveryQueryService;

	@GetMapping("/{deliveryId}")
	public ResponseEntity<GetDeliveryInfoResponseDto> getDeliveryInfo(
		@RequestHeader("X-User-Id") UUID userId,
		@RequestHeader("X-User-Role") String userRole,
		@PathVariable UUID deliveryId
	) {
		GetDeliveryInfoResponseDto responseDto = deliveryQueryService.getDeliveryInfo(deliveryId, userId, userRole);
		return ResponseEntity.ok(responseDto);
	}
}
