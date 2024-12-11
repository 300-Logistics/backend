package com.example.hub.service;

import com.example.hub.domain.model.entity.Hub;
import com.example.hub.dto.request.HubRequest;
import com.example.hub.dto.response.DeleteHubResponse;
import com.example.hub.dto.response.HubResponse;
import com.example.hub.libs.exception.CustomException;
import com.example.hub.libs.exception.ErrorCode;
import com.example.hub.repository.HubJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService { // TODO : 시큐리티 끝나면 role MASTER 검증

    private final HubJpaRepository hubJpaRepository;

    @Transactional
    public HubResponse createHub(HubRequest request, UUID userId, String role) {
        Hub hub = hubJpaRepository.save(Hub.create(request, userId));
        return new HubResponse(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
    }

    @Transactional
    public HubResponse updateHub(HubRequest request, UUID hubId, UUID userId, String role) {
        Hub hub = hubJpaRepository.findById(hubId).orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
        hub.update(request, userId);
        return new HubResponse(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
    }

    @Transactional
    public DeleteHubResponse deleteHub(UUID hubId, UUID userId, String role) {
        Hub hub = hubJpaRepository.findById(hubId).orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
        hub.delete(userId);
        return new DeleteHubResponse(true, "Hub deleted successfully.");
    }

    @Transactional(readOnly = true)
    public HubResponse getHub(UUID hubId) {
        Hub hub = hubJpaRepository.findById(hubId).orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
        return new HubResponse(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
    }
}
