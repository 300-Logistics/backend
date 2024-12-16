package com.example.order.infrastructure.client;

import com.example.order.application.dto.DeliveryResponse;
import com.example.order.infrastructure.client.request.DeliveryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery")
public interface DeliveryClient {

    @PostMapping("/api/delivery")
    DeliveryResponse createDelivery(@RequestBody DeliveryRequest deliveryRequest);

}
