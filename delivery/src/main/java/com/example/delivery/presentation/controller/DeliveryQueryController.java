package com.example.delivery.presentation.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.dto.GetDeliveryInfoResponseDto;
import com.example.delivery.application.dto.GetDeliveryListResponseDto;
import com.example.delivery.application.service.interfaces.DeliveryQueryService;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;
import com.example.delivery.presentation.dto.GetDeliveryListRequestDto;

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

	@GetMapping
	public ResponseEntity<Page<GetDeliveryListResponseDto>> getDeliveryList(
		@RequestHeader("X-User-Id") UUID userId,
		GetDeliveryListRequestDto requestDto,
		Pageable pageable
	) {
		if (pageable.getPageNumber() < 0) {
			throw new CustomException(ErrorCode.INVALID_PAGE_NUMBER_NOT_BELOW_ZERO);
		}
		if (pageable.getPageSize() <= 0) {
			throw new CustomException(ErrorCode.INVALID_PAGE_SIZE);
		}

		Page<GetDeliveryListResponseDto> responseDto = deliveryQueryService.getDeliveryList(userId, requestDto.isDeleted(),
			requestDto.isCompleted(), pageable);

		return ResponseEntity.ok(responseDto);
	}
}
