package com.example.auth.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.application.service.DeliveryStaffService;
import com.example.auth.presentation.dto.DeliveryStaffRegisterRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-staffs")
public class DeliveryStaffController {

	private final DeliveryStaffService deliveryStaffService;

	@PostMapping("/register")
	public ResponseEntity<String> registerDeliveryStaff(
		@RequestHeader("X-User-Role") String userRole,
		@RequestBody @Valid DeliveryStaffRegisterRequestDto requestDto) {
		deliveryStaffService.registerDeliveryStaff(requestDto, userRole);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body("배송 담당자 등록 완료");
	}

	@GetMapping("/hub")
	public ResponseEntity<List<UUID>> getHubDeliveryStaffList() {
		List<UUID> responseDtoList = deliveryStaffService.getHubDeliveryStaffList();
		return ResponseEntity.ok(responseDtoList);
	}
	@GetMapping("/{hubId}")
	public ResponseEntity<List<UUID>> getCompanyDeliveryStaffList(
		@PathVariable UUID hubId
	) {
		List<UUID> responseDtoList = deliveryStaffService.getCompanyDeliveryStaffList(hubId);
		return ResponseEntity.ok(responseDtoList);
	}
}
