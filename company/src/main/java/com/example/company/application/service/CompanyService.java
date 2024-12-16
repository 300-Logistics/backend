package com.example.company.application.service;

import com.example.company.application.dto.HubResponse;
import com.example.company.domain.model.Company;
import com.example.company.domain.repository.CompanyRepository;
import com.example.company.infrastructure.HubClient;
import com.example.company.presentation.request.CompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    public UUID create(CompanyRequest request) {
        UUID hubId = request.getHubId();
        HubResponse hub = hubClient.getHubById(hubId);

        Company company = new Company(hub.id(), request.getName(), request.getAddress(), request.getCompanyType());

        Company savedCompany = companyRepository.save(company);

        return savedCompany.getCompanyId();
    }

}
