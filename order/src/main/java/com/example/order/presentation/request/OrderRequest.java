package com.example.order.presentation.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderRequest {

    private UUID supplierId;
    private UUID customerId;
    private UUID productId;
    private int count;
    private String requests;
    private UUID deliveryId;    // null 가능

    public OrderRequest(UUID supplierId, UUID customerId, UUID productId, int count, String requests, UUID deliveryId) {
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
        this.requests = requests;
        this.deliveryId = deliveryId;
    }
}
