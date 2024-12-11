package com.example.product.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(nullable = false)
    private UUID companyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private int initialStock;

    @Column(nullable = false)
    private int currentStock;

    public Product(UUID companyId, String name, int initialStock) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("상품명을 입력하세요");
        }

        if (initialStock <= 0) {
            throw new IllegalArgumentException("상품 재고는 0 이상이어야 합니다");
        }

        this.companyId = companyId;
        this.name = name;
        this.initialStock = initialStock;
        this.currentStock = initialStock;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }


}
