package com.example.order.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

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

    @Column(nullable = true)
    private UUID deliveryId;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = true)
    private String requests;

    @Builder
    public Order(UUID productId, UUID supplierId, UUID customerId,
                 int count, String requests) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.count = count;
        this.requests = requests;
    }

    public void setDeleted() {
        this.isDeleted = true;
        this.setDeletedAt(LocalDateTime.now());
    }

    public void updateDelivery(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void update(UUID customerId, UUID supplierId, UUID productId, int count, String requests, UUID deliveryId) {
        this.customerId = customerId;
        this.supplierId = supplierId;
        this.productId = productId;
        this.count = count;
        this.requests = requests;
        this.deliveryId = deliveryId;
    }
}
