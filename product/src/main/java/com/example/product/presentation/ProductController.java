package com.example.product.presentation;

import com.example.product.application.dto.ProductDto;
import com.example.product.application.service.ProductService;
import com.example.product.presentation.request.ProductRequest;
import com.example.product.presentation.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProductRequest request) {
        ProductDto productDto = productService.create(request);

        return toResponseEntity(HttpStatus.CREATED, productDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> put(@RequestBody ProductRequest request, @PathVariable UUID productId) {
        ProductDto productDto = productService.update(request, productId);

        return toResponseEntity(HttpStatus.OK, productDto);
    }

    private ResponseEntity<?> toResponseEntity(HttpStatus httpStatus, ProductDto productDto) {
        return ResponseEntity.status(httpStatus)
                .body(ProductResponse.builder()
                        .productId(productDto.getProductId())
                        .companyId(productDto.getCompanyId())
                        .hubId(productDto.getHubId())
                        .name(productDto.getName())
                        .currentStock(productDto.getCurrentStock())
                        .initialStock(productDto.getInitialStock())
                        .isDeleted(productDto.isDeleted())
                        .build());
    }

}
