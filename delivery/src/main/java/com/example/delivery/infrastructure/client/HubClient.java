package com.example.delivery.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.delivery.application.dto.HubPathResponse;
import com.example.delivery.application.dto.HubResponse;

@FeignClient(name = "hub")
public interface HubClient {

	@GetMapping("/api//hubs/{hubId}")
	ResponseEntity<HubResponse> getHub(@PathVariable UUID hubId);

	@GetMapping("/api/hub-paths")
	ResponseEntity<HubPathResponse> searchHubPath(
		@RequestParam UUID startHudId,
		@RequestParam UUID endHubId
	);
}
