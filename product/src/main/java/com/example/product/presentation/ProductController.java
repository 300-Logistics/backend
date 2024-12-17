package com.example.product.presentation;

import com.example.product.application.dto.ProductDto;
import com.example.product.application.service.ProductService;
import com.example.product.presentation.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable UUID productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> get(@PathVariable UUID productId) {
        ProductDto productDto = productService.find(productId);

        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdBy") String sortBy) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        Page<ProductDto> productDtos = productService.find(keyword, page - 1, size, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllByCompanyId(@RequestBody Map<String, Object> request) {
        productService.deleteAllByCompanyId(UUID.fromString(request.get("companyId").toString()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
