package com.example.hub.controller;

import com.example.hub.dto.request.HubRequest;
import com.example.hub.dto.response.DeleteHubResponse;
import com.example.hub.dto.response.HubResponse;
import com.example.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = {"/api"})
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping("/hubs")
    public ResponseEntity<HubResponse> createHub(
        @RequestBody HubRequest request,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role
    ) {
        return ResponseEntity.ok(hubService.createHub(request, userId, role));
    }

    @PutMapping("/hubs/{hubId}")
    public ResponseEntity<HubResponse> updateHub(
        @RequestBody HubRequest request,
        @PathVariable UUID hubId,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role
    ) {
        return ResponseEntity.ok(hubService.updateHub(request, hubId, userId, role));
    }

    @DeleteMapping("/hubs/{hubId}")
    public ResponseEntity<DeleteHubResponse> deleteHub(
        @PathVariable UUID hubId,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role
    ) {
        return ResponseEntity.ok(hubService.deleteHub(hubId, userId, role));
    }

}
