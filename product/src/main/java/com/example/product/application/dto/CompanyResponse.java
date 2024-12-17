package com.example.product.application.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CompanyResponse {

    private UUID companyId;
    private UUID hubId;
    private String name;
    private String address;
    private String companyType;

    public CompanyResponse(UUID companyId, UUID hubId, String name, String address, String companyType) {
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.address = address;
        this.companyType = companyType;
    }
}
