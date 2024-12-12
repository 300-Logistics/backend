package com.example.hub.service;

import com.example.hub.domain.model.entity.Hub;
import com.example.hub.domain.model.entity.HubPath;
import com.example.hub.dto.request.CreateHubPathRequest;
import com.example.hub.dto.request.UpdateHubPathRequest;
import com.example.hub.dto.response.HubPathResponse;
import com.example.hub.libs.exception.CustomException;
import com.example.hub.libs.exception.ErrorCode;
import com.example.hub.repository.HubJpaRepository;
import com.example.hub.repository.HubPathJpaRepository;
import com.example.hub.repository.HubPathQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubPathService { // TODO : 시큐리티 끝나면 role MASTER 검증

    private final HubJpaRepository hubJpaRepository;
    private final HubPathJpaRepository hubPathJpaRepository;
    private final HubPathQueryRepository hubPathQueryRepository;

    @Transactional
    public HubPathResponse createHubPath(CreateHubPathRequest request, UUID userId, String role) {
        Hub startHub = getHubById(request.startHubId());
        Hub endHub = getHubById(request.endHubId());
        HubPath hubPath = hubPathJpaRepository.save(HubPath.create(request, startHub, endHub, userId));
        return convertToHubPathResponse(hubPath);
    }

    @Transactional
    public HubPathResponse updateHubPath(UpdateHubPathRequest request, UUID hubPathId, UUID userId, String role) {
        HubPath hubPath = getHubPathById(hubPathId);
        hubPath.update(request, userId);
        return convertToHubPathResponse(hubPath);
    }

    private Hub getHubById(UUID hubId) {
        return hubJpaRepository.findById(hubId)
            .orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
    }

    private HubPath getHubPathById(UUID hubPathId) {
        return hubPathJpaRepository.findById(hubPathId)
            .orElseThrow(() -> new CustomException(ErrorCode.HUB_PATH_NOT_FOUND));
    }

    private HubPathResponse convertToHubPathResponse(HubPath hubPath) {
        return new HubPathResponse(
            hubPath.getId(),
            hubPath.getStartHub().getId(),
            hubPath.getEndHub().getId(),
            hubPath.getDuration(),
            hubPath.getDistance()
        );
    }

}
