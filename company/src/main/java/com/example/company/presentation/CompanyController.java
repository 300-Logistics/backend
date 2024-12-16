package com.example.company.presentation;

import com.example.company.application.service.CompanyService;
import com.example.company.presentation.request.CompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CompanyRequest companyRequest) {
        UUID companyId = companyService.create(companyRequest);
        return ResponseEntity.created(URI.create(companyId.toString())).build();
    }

}
