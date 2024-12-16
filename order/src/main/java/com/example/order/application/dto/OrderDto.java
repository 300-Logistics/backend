package com.example.order.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderDto {

    private UUID orderId;
    private UUID supplierId;
    private UUID customerId;
    private UUID productId;
    private int count;
    private UUID deliveryId;
    private String requests;

    @Builder
    public OrderDto(UUID orderId, UUID supplierId, UUID customerId, UUID productId, int count, UUID deliveryId, String requests) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
        this.deliveryId = deliveryId;
        this.requests = requests;
    }
}
