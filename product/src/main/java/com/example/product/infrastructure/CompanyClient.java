package com.example.product.infrastructure;

import com.example.product.application.dto.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company")
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    CompanyResponse getCompanyById(@PathVariable UUID companyId);

}
