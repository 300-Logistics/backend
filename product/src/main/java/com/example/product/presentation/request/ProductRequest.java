package com.example.product.presentation.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductRequest {

    private UUID companyId;
    private String name;
    private boolean isDeleted;
    private int initialStock;
    private int currentStock;

    public ProductRequest(UUID companyId, String name, boolean isDeleted, int initialStock, int currentStock) {
        this.companyId = companyId;
        this.name = name;
        this.isDeleted = isDeleted;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
    }

}
