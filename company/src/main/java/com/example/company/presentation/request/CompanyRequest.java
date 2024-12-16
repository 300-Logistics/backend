package com.example.company.presentation.request;

import com.example.company.domain.model.CompanyType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CompanyRequest {

    private String name;
    private String address;
    private CompanyType companyType;
    private UUID hubId;

    public CompanyRequest(String name, String address, CompanyType companyType, UUID hubId) {
        this.name = name;
        this.address = address;
        this.companyType = companyType;
        this.hubId = hubId;
    }
}
