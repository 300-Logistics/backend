package com.example.slack.client;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth")
public interface DeliveryStaffClient {

	@GetMapping("/api//delivery-staffs/{deliveryStaffId}")
	Optional<String> getSlackId(@PathVariable UUID deliveryStaffId);
}
