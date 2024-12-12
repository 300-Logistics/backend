package com.example.delivery.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.delivery.application.dto.HubRouteResponseDto;

@FeignClient(name = "hub")
public interface HubClient {

	@GetMapping("/hub-paths")
	HubRouteResponseDto getHubRoute(@RequestParam UUID startHubId, @RequestParam UUID destinationHubId);
}
