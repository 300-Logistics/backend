package com.example.product.application.service;

import com.example.product.application.dto.ProductDto;
import com.example.product.domain.model.Product;
import com.example.product.domain.repository.ProductRepository;
import com.example.product.presentation.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto create(ProductRequest request) {
        // todo: company 엔터티 검색 후 product 생성
        UUID companyId = request.getCompanyId();

        Product product = new Product(companyId, request.getName(), request.getInitialStock());
        Product savedProduct = productRepository.save(product);

        return ProductDto.builder()
                .productId(savedProduct.getProductId())
                .companyId(savedProduct.getCompanyId())
                .hubId(UUID.randomUUID()) // todo: 허브 id는 company 엔터티에서 가져오기
                .name(savedProduct.getName())
                .currentStock(savedProduct.getCurrentStock())
                .initialStock(savedProduct.getInitialStock())
                .isDeleted(savedProduct.isDeleted())
                .build();
    }

}
