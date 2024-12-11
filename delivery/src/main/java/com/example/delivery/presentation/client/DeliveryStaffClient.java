package com.example.delivery.presentation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery-staff-service")
public interface DeliveryStaffClient {

	//    @GetMapping(uri)
	//    DeliveryStaffDto getDeliveryStaffById(@PathVariable UUID id);
}
