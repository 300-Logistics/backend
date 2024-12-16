package com.example.hub.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hub.dto.request.HubPathRequest;
import com.example.hub.dto.response.DeleteResponse;
import com.example.hub.dto.response.HubPathResponse;
import com.example.hub.service.HubPathService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = {"/api/hub-paths"})
@RequiredArgsConstructor
public class HubPathController {

    private final HubPathService hubPathService;

    @PostMapping()
    public ResponseEntity<HubPathResponse> createHubPath(
        @RequestBody HubPathRequest request,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role
    ) {
        return ResponseEntity.ok(hubPathService.createHubPath(request.startHubId(), request.endHubId(), userId, role));
    }

    @DeleteMapping("/{hubPathId}")
    public ResponseEntity<DeleteResponse> deleteHubPath(
        @PathVariable UUID hubPathId,
        @RequestHeader(value = "userId") UUID userId,
        @RequestHeader(value = "role") String role
    ) {
        return ResponseEntity.ok(hubPathService.deleteHubPath(hubPathId, userId, role));
    }

    @GetMapping()
    public ResponseEntity<HubPathResponse> searchHubPath(
        @RequestParam UUID startHubId,
        @RequestParam UUID endHubId) {
        return ResponseEntity.ok(hubPathService.searchHubPath(startHubId, endHubId));
    }

}
