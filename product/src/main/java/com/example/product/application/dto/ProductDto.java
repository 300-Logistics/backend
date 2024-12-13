package com.example.product.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductDto {

    private UUID productId;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private int initialStock;
    private int currentStock;

    @Builder
    public ProductDto(UUID productId, UUID companyId, UUID hubId, String name,
                      int initialStock, int currentStock) {
        this.productId = productId;
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
    }
}
