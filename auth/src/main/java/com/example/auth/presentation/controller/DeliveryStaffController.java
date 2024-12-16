package com.example.auth.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.application.service.DeliveryStaffService;
import com.example.auth.presentation.dto.DeliveryStaffRegisterRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-staff")
public class DeliveryStaffController {

	private final DeliveryStaffService deliveryStaffService;

	@PostMapping("/register")
	public ResponseEntity<String> registerDeliveryStaff(
		@RequestBody @Valid DeliveryStaffRegisterRequestDto requestDto) {
		deliveryStaffService.registerDeliveryStaff(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body("배송 담당자 등록 완료");
	}
}
