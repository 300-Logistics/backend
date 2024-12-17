package com.example.hub.application.service;

import com.example.hub.domain.model.entity.Hub;
import com.example.hub.domain.model.entity.HubConnection;
import com.example.hub.domain.model.entity.HubPath;
import com.example.hub.infrastructure.client.AuthClient;
import com.example.hub.presentation.dto.response.DeleteResponse;
import com.example.hub.presentation.dto.response.HubPathResponse;
import com.example.hub.libs.exception.CustomException;
import com.example.hub.libs.exception.ErrorCode;
import com.example.hub.infrastructure.repository.HubConnectionJpaRepository;
import com.example.hub.infrastructure.repository.HubJpaRepository;
import com.example.hub.infrastructure.repository.HubPathJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HubPathService {

    private final HubJpaRepository hubJpaRepository;
    private final HubPathJpaRepository hubPathJpaRepository;
    private final HubConnectionJpaRepository hubConnectionJpaRepository;
    private final AuthClient authClient;

    @Transactional
    public HubPathResponse createHubPath(UUID startHubId, UUID endHubId, UUID userId, String role, String token) {
        validateMasterWithToken(role, token);
        HubPath existingHubPath =
            hubPathJpaRepository.findByStartHubIdAndEndHubIdAndDeletedAtIsNull(startHubId, endHubId);
        if (existingHubPath != null) {
            return convertToHubPathResponse(existingHubPath);
        }

        // 허브 연결 정보를 DB에서 가져옴
        List<HubConnection> connections = hubConnectionJpaRepository.findAll();
        Map<UUID, List<HubConnection>> graph = buildStaticGraph(connections);

        // 다익스트라 알고리즘 실행하여 최적 경로 계산
        Map<String, Object> result = dijkstra(graph, startHubId, endHubId);

        // 출발 허브와 도착 허브 엔티티 조회
        Hub startHub = getHubById(startHubId);
        Hub endHub = getHubById(endHubId);

        // 최적 경로 결과에서 duration과 distance 추출
        int duration = (int) result.get("duration");
        double distance = (double) result.get("distance");

        // 최적 경로 결과를 기반으로 HubPath 엔티티 생성 및 저장
        HubPath hubPath = hubPathJpaRepository.save(HubPath.create(startHub, endHub, duration, distance, userId));
        return convertToHubPathResponse(hubPath);
    }

    @Transactional
    public DeleteResponse deleteHubPath(UUID hubPathId, UUID userId, String role, String token) {
        validateMasterWithToken(role, token);
        HubPath hubPath = hubPathJpaRepository.findById(hubPathId)
            .orElseThrow(() -> new CustomException(ErrorCode.HUB_PATH_NOT_FOUND));
        hubPath.delete(userId);
        return new DeleteResponse(true, "Hub Path deleted successfully.");
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubPathCache", key = "#startHubId + ':' + #endHubId")
    public HubPathResponse searchHubPath(UUID startHubId, UUID endHubId, String token) {
        authClient.validateToken(token);
        HubPath hubPath =
            hubPathJpaRepository.findByStartHubIdAndEndHubIdAndDeletedAtIsNull(startHubId, endHubId);
        return convertToHubPathResponse(hubPath);
    }

    private Hub getHubById(UUID hubId) {
        return hubJpaRepository.findById(hubId)
            .orElseThrow(() -> new CustomException(ErrorCode.HUB_NOT_FOUND));
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


    private Map<UUID, List<HubConnection>> buildStaticGraph(List<HubConnection> connections) {
        Map<UUID, List<HubConnection>> graph = new HashMap<>();
        for (HubConnection connection : connections) {
            graph.computeIfAbsent(connection.getStartHub().getId(), key -> new ArrayList<>()).add(connection);
        }
        return graph;
    }

    /**
     * 다익스트라 알고리즘을 사용하여 최단 경로 계산
     * @param graph 허브 간 연결 정보
     * @param start 출발 허브 ID
     * @param end 도착 허브 ID
     * @return 최단 거리와 소요 시간을 담은 결과
     */
    private Map<String, Object> dijkstra(Map<UUID, List<HubConnection>> graph, UUID start, UUID end) {
        // 최소 소요 시간과 거리 저장
        Map<UUID, Integer> durations = new HashMap<>();
        Map<UUID, Double> distances = new HashMap<>();
        PriorityQueue<UUID> queue = new PriorityQueue<>(Comparator.comparingInt(durations::get));

        // 초기화
        for (UUID hubId : graph.keySet()) {
            durations.put(hubId, Integer.MAX_VALUE);
            distances.put(hubId, Double.MAX_VALUE);
        }
        durations.put(start, 0);
        distances.put(start, 0.0);

        queue.add(start);

        while (!queue.isEmpty()) {
            UUID currentHub = queue.poll();

            if (currentHub.equals(end)) {
                // 최단 거리 및 시간 반환
                Map<String, Object> result = new HashMap<>();
                result.put("duration", durations.get(end));
                result.put("distance", distances.get(end));
                return result;
            }

            // 현재 허브와 연결된 모든 허브 탐색
            for (HubConnection connection : graph.getOrDefault(currentHub, new ArrayList<>())) {
                UUID neighbor = connection.getEndHub().getId();
                int newDuration = durations.get(currentHub) + connection.getDuration();
                double newDistance = distances.get(currentHub) + connection.getDistance();

                // 더 짧은 경로 발견 시 업데이트
                if (newDuration < durations.get(neighbor)) {
                    durations.put(neighbor, newDuration);
                    distances.put(neighbor, newDistance);
                    queue.add(neighbor);
                }
            }
        }

        throw new IllegalArgumentException("경로를 찾을 수 없습니다."); // 경로가 없는 경우 예외 처리
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
