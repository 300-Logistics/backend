package com.example.hub.controller;

import com.example.hub.dto.request.HubPathRequest;
import com.example.hub.dto.response.DeleteResponse;
import com.example.hub.dto.response.HubPathResponse;
import com.example.hub.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<HubPathResponse> searchHubPath(@RequestBody HubPathRequest request) {
        return ResponseEntity.ok(hubPathService.searchHubPath(request.startHubId(), request.endHubId()));
    }

}
