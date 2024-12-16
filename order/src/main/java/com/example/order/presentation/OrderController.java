package com.example.order.presentation;

import com.example.order.application.dto.OrderDto;
import com.example.order.application.service.OrderService;
import com.example.order.presentation.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<?> get(@PathVariable UUID orderId) {
        OrderDto orderDto = orderService.find(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdBy") String sortBy
    ) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        if (page <= 0) {
            page = 1;
        }
        if (!(sortBy.equals("createdBy") || sortBy.equals("updatedBy"))) {
            sortBy = "createdBy";
        }

        Page<OrderDto> orderDtos = orderService.findAll(page - 1, size, sortBy);
        return ResponseEntity.ok(orderDtos);
    }


}
