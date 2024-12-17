package com.example.product.application.service;

import com.example.product.application.dto.ProductDto;
import com.example.product.domain.model.Product;
import com.example.product.domain.repository.ProductRepository;
import com.example.product.infrastructure.CompanyClient;
import com.example.product.libs.exception.CustomException;
import com.example.product.libs.exception.ErrorCode;
import com.example.product.presentation.request.ProductRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyClient companyClient;

    public ProductDto create(ProductRequest request) {
        validateExistingCompany(request.getCompanyId());

        Product product = new Product(request.getCompanyId(), request.getName(), request.getInitialStock());
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

    public ProductDto find(UUID productId) {
        Product product = productRepository.findProductByProductIdAndIsDeleted(productId, false)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return toProductDto(product);
    }

    public Page<ProductDto> find(String keyword, int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productList = productRepository.findProductByNameAndIsDeleted(keyword, false, pageable);

        return productList.map(this::toProductDto);
    }

    private void validateExistingCompany(UUID companyId) {
        try {
            companyClient.getCompanyById(companyId);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.COMPANY_NOT_FOUND);
        }
    }

    private ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .companyId(product.getCompanyId())
                .hubId(UUID.randomUUID()) // todo: 허브 id는 company 엔터티에서 가져오기
                .name(product.getName())
                .currentStock(product.getCurrentStock())
                .initialStock(product.getInitialStock())
                .build();
    }


}
