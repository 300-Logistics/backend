package com.example.order.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID supplierId;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column
    private String requests;

    @Builder
    public Order(UUID productId, UUID supplierId, UUID customerId,
                 int count, UUID deliveryId, boolean isDeleted, String requests) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.count = count;
        this.deliveryId = deliveryId;
        this.isDeleted = isDeleted;
        this.requests = requests;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }
}
