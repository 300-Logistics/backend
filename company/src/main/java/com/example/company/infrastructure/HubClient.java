package com.example.company.infrastructure;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.company.application.dto.HubResponse;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/api//hubs/{hubId}")
    ResponseEntity<HubResponse> getHub(@PathVariable UUID hubId, @RequestHeader("Authorization") String token);

}
