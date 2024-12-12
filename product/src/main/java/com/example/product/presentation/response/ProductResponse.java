package com.example.product.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductResponse {

    private UUID productId;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private boolean isDeleted;
    private int initialStock;
    private int currentStock;

    @Builder
    public ProductResponse(UUID productId, UUID companyId, UUID hubId, String name,
                           boolean isDeleted, int initialStock, int currentStock) {
        this.productId = productId;
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.isDeleted = isDeleted;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
    }
}
