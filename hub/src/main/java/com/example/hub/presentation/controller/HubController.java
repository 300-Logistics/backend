package com.example.hub.presentation.controller;

import com.example.hub.application.service.HubService;
import com.example.hub.presentation.dto.request.HubRequest;
import com.example.hub.presentation.dto.response.DeleteResponse;
import com.example.hub.presentation.dto.response.HubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = {"/api/hubs"})
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping()
    public ResponseEntity<HubResponse> createHub(
        @RequestBody HubRequest request,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role,
        @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.createHub(request, userId, role, token));
    }

    @PutMapping("/{hubId}")
    public ResponseEntity<HubResponse> updateHub(
        @RequestBody HubRequest request,
        @PathVariable UUID hubId,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role,
        @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.updateHub(request, hubId, userId, role, token));
    }

    @DeleteMapping("/{hubId}")
    public ResponseEntity<DeleteResponse> deleteHub(
        @PathVariable UUID hubId,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role,
        @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.deleteHub(hubId, userId, role, token));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<HubResponse> getHub(
        @PathVariable UUID hubId,
        @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.getHub(hubId, token));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HubResponse>> searchHubs(
        Pageable pageable,
        @RequestParam String keyword,
        @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.ok(hubService.searchHubs(pageable, keyword, token));
    }

}
