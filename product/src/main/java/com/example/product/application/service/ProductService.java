package com.example.product.application.service;

import com.example.product.application.dto.ProductDto;
import com.example.product.domain.model.Product;
import com.example.product.domain.repository.ProductRepository;
import com.example.product.libs.exception.CustomException;
import com.example.product.libs.exception.ErrorCode;
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

        return toProductDto(savedProduct);
    }

    public ProductDto update(ProductRequest request, UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.update(request.getCompanyId(), request.getName(), request.isDeleted(),
                request.getInitialStock(), request.getCurrentStock());

        return toProductDto(product);
    }

    public void delete(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setDeleted();
    }


    private ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .companyId(product.getCompanyId())
                .hubId(UUID.randomUUID()) // todo: 허브 id는 company 엔터티에서 가져오기
                .name(product.getName())
                .currentStock(product.getCurrentStock())
                .initialStock(product.getInitialStock())
                .isDeleted(product.isDeleted())
                .build();
    }

}
