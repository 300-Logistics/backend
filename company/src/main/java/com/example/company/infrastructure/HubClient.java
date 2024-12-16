package com.example.company.infrastructure;

import com.example.company.application.dto.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}")
    HubResponse getHubById(@PathVariable("hubId") UUID hubId);

}
