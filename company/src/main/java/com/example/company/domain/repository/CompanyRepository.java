package com.example.company.domain.repository;

import com.example.company.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByCompanyIdAndDeletedAtNull(UUID id);
    Page<Company> findByNameAndDeletedAtNull(String name, Pageable pageable);

}
