package com.example.hub.controller;

import com.example.hub.dto.request.HubRequest;
import com.example.hub.dto.response.HubResponse;
import com.example.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping("/hubs")
    public ResponseEntity<HubResponse> createHub(@RequestBody HubRequest request) {
        return ResponseEntity.ok(hubService.createHub(request));
    }

}
