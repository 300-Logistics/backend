package com.example.order.infrastructure.client;

import com.example.order.application.dto.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company")
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    CompanyResponse findCompanyById(@PathVariable UUID companyId);

}
