package com.example.order.infrastructure.client;

import com.example.order.application.dto.DeliveryResponse;
import com.example.order.infrastructure.client.request.DeliveryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "delivery")
public interface DeliveryClient {

    @PostMapping("/api/delivery")
    DeliveryResponse createDelivery(@RequestBody DeliveryRequest deliveryRequest);

    @DeleteMapping("/api/delivery/{deliveryId}")
    ResponseEntity<Void> cancelDelivery(UUID deliveryId);

}
