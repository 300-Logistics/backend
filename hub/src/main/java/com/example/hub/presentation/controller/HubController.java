package com.example.hub.presentation.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hub.application.service.HubService;
import com.example.hub.presentation.dto.request.HubRequest;
import com.example.hub.presentation.dto.response.DeleteResponse;
import com.example.hub.presentation.dto.response.HubResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = {"/api/hubs"})
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping()
    public ResponseEntity<HubResponse> createHub(
        @RequestBody HubRequest request,
        @RequestHeader("X-User-Id") UUID userId,
        @RequestHeader("X-User-Role") String role,
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.createHub(request, userId, role, token));
    }

    @PutMapping("/{hubId}")
    public ResponseEntity<HubResponse> updateHub(
        @RequestBody HubRequest request,
        @PathVariable UUID hubId,
        @RequestHeader("X-User-Id") UUID userId,
        @RequestHeader("X-User-Role") String role,
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.updateHub(request, hubId, userId, role, token));
    }

    @DeleteMapping("/{hubId}")
    public ResponseEntity<DeleteResponse> deleteHub(
        @PathVariable UUID hubId,
        @RequestHeader("X-User-Id") UUID userId,
        @RequestHeader("X-User-Role") String role,
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.deleteHub(hubId, userId, role, token));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<HubResponse> getHub(
        @PathVariable UUID hubId,
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.getHub(hubId, token));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HubResponse>> searchHubs(
        Pageable pageable,
        @RequestParam String keyword,
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.searchHubs(pageable, keyword, token));
    }

}
