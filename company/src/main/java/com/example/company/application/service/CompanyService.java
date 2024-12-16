package com.example.company.application.service;

import com.example.company.application.dto.CompanyDto;
import com.example.company.domain.model.Company;
import com.example.company.domain.repository.CompanyRepository;
import com.example.company.infrastructure.HubClient;
import com.example.company.libs.exception.CustomException;
import com.example.company.libs.exception.ErrorCode;
import com.example.company.presentation.request.CompanyRequest;
import feign.FeignException;
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
        validateExistingHub(request.getHubId());

        Company company = new Company(request.getHubId(), request.getName(), request.getAddress(), request.getCompanyType());
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getCompanyId();
    }

    public CompanyDto update(CompanyRequest request, UUID companyId) {
        validateExistingHub(request.getHubId());

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        company.update(request.getHubId(), request.getName(), request.getAddress(), request.getCompanyType());

        return toCompanyDto(company);
    }

    private void validateExistingHub(UUID hubId) {
        try {
            hubClient.getHubById(hubId);
        } catch (FeignException e) {
            throw new CustomException(ErrorCode.HUB_NOT_FOUND);
        }
    }

    private CompanyDto toCompanyDto(Company company) {
        return new CompanyDto(company.getCompanyId(), company.getHubId(), company.getName(),
                company.getAddress(), company.getCompanyType());
    }

}
