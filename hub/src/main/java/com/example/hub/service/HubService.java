package com.example.hub.service;

import com.example.hub.domain.model.entity.Hub;
import com.example.hub.dto.request.HubRequest;
import com.example.hub.dto.response.HubResponse;
import com.example.hub.repository.HubJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubService { // TODO : role 검증

    private final HubJpaRepository hubJpaRepository;

    public HubResponse createHub(HubRequest request) {
        Hub hub = hubJpaRepository.save(Hub.create(request));
        return new HubResponse(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
    }
}
