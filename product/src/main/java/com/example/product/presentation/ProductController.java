package com.example.product.presentation;

import com.example.product.application.dto.ProductDto;
import com.example.product.application.service.ProductService;
import com.example.product.presentation.request.ProductRequest;
import com.example.product.presentation.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> put(@RequestBody ProductRequest request, @PathVariable UUID productId) {
        ProductDto productDto = productService.update(request, productId);

        return toResponseEntity(HttpStatus.OK, productDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable UUID productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> get(@PathVariable UUID productId) {
        ProductDto productDto = productService.find(productId);

        return toResponseEntity(HttpStatus.OK, productDto);
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        Page<ProductDto> productDtos = productService.find(keyword, page - 1, size, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
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
