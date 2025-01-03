package com.example.hub.application.service;

import com.example.hub.domain.model.entity.Hub;
import com.example.hub.infrastructure.client.AuthClient;
import com.example.hub.infrastructure.repository.HubJpaRepository;
import com.example.hub.infrastructure.repository.HubQueryRepository;
import com.example.hub.libs.exception.CustomException;
import com.example.hub.libs.exception.ErrorCode;
import com.example.hub.presentation.dto.request.HubRequest;
import com.example.hub.presentation.dto.response.DeleteResponse;
import com.example.hub.presentation.dto.response.HubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubJpaRepository hubJpaRepository;
    private final HubQueryRepository hubQueryRepository;
    private final AuthClient authClient;

    @Transactional
    public HubResponse createHub(HubRequest request, UUID userId, String role, String token) {
        validateMasterWithToken(role, token);
        Hub hub = hubJpaRepository.save(Hub.create(request, userId));
        return convertToHubResponse(hub);
    }

    @Transactional
    public HubResponse updateHub(HubRequest request, UUID hubId, UUID userId, String role, String token) {
        validateMasterWithToken(role, token);
        Hub hub = getHubById(hubId);
        hub.update(request, userId);
        return convertToHubResponse(hub);
    }

    @Transactional
    public DeleteResponse deleteHub(UUID hubId, UUID userId, String role, String token) {
        validateMasterWithToken(role, token);
        Hub hub = getHubById(hubId);
        hub.delete(userId);
        return new DeleteResponse(true, "Hub deleted successfully.");
    }

    @Transactional(readOnly = true)
    public HubResponse getHub(UUID hubId, String token) {
        authClient.validateToken(token);
        Hub hub = getHubById(hubId);
        return convertToHubResponse(hub);
    }

    @Transactional(readOnly = true)
    public Page<HubResponse> searchHubs(Pageable pageable, String keyword, String token) {
        authClient.validateToken(token);
        return hubQueryRepository.searchHubs(pageable, keyword);
    }

    private Hub getHubById(UUID hubId) {
        return hubJpaRepository.findById(hubId)
            .orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
    }

    private HubResponse convertToHubResponse(Hub hub) {
        return new HubResponse(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
    }

    private void validateMasterWithToken(String role, String token) {
        validateMaster(role);
        authClient.validateMasterToken(token);
    }

    private static void validateMaster(String role) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
