package com.example.company.application.dto;

import com.example.company.domain.model.CompanyType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CompanyDto {

    private UUID companyId;
    private UUID hubId;
    private String name;
    private String address;
    private CompanyType companyType;

    public CompanyDto(UUID companyId, UUID hubId, String name, String address, CompanyType companyType) {
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.address = address;
        this.companyType = companyType;
    }
}
