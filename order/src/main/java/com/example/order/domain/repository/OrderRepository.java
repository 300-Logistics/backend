package com.example.order.domain.repository;

import com.example.order.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderIdAndDeletedAtNull(UUID orderId);
    Page<Order> findAllByDeletedAtNull(Pageable pageable);
}
