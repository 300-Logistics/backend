package com.example.order.presentation;

import com.example.order.application.dto.OrderDto;
import com.example.order.application.service.OrderService;
import com.example.order.presentation.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody OrderRequest orderRequest) {
        UUID orderId = orderService.create(orderRequest);
        return ResponseEntity.created(URI.create("/api/orders/" + orderId)).build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> put(@PathVariable UUID orderId, @RequestBody OrderRequest orderRequest) {
        OrderDto orderDto = orderService.update(orderId, orderRequest);
        return ResponseEntity.ok(orderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> delete(@PathVariable UUID orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }


}
