package com.example.product.domain.repository;

import com.example.product.domain.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findProductByProductIdAndIsDeleted(UUID productId, boolean deleted);

    Page<Product> findProductByNameAndIsDeleted(String name, boolean isDeleted, Pageable pageable);

    List<Product> findAllByCompanyIdAndDeletedAtNull(UUID companyId);
}
