package com.example.product.presentation;

import com.example.product.application.dto.ProductDto;
import com.example.product.application.service.ProductService;
import com.example.product.presentation.request.ProductRequest;
import com.example.product.presentation.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProductRequest request) {
        ProductDto productDto = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
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
