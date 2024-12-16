package com.example.delivery.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth")
public interface DeliveryStaffClient {

	@GetMapping("/delivery-staffs/hub")
	List<UUID> getHubDeliveryStaffList();

	@GetMapping("/delivery-staffs/{destinationHubId}")
	List<UUID> getCompanyDeliveryStaffList(@PathVariable UUID destinationHubId);

}
