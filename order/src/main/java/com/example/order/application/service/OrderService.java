package com.example.order.application.service;

import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.presentation.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public UUID create(OrderRequest request) {
        Order order = Order.builder()
                .productId(request.getProductId())
                .count(request.getCount())
                .customerId(request.getCustomerId())
                .supplierId(request.getSupplierId())
                .requests(request.getRequests())
                .build();

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getOrderId();
    }

}
