package com.example.hub.domain.model.entity;

import com.example.hub.presentation.dto.request.UpdateHubPathRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "p_hub_path"
)
public class HubPath extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub;

    @Column(name = "distance", nullable = false)
    private double distance; // 이동 거리 (km)

    @Column(name = "duration", nullable = false)
    private int duration; // 소요 시간 (분)

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static HubPath create(Hub startHub, Hub endHub, int duration, double distance, UUID userId) {
        HubPath hubPath = HubPath.builder()
            .startHub(startHub)
            .endHub(endHub)
            .distance(distance)
            .duration(duration)
            .isDeleted(false)
            .build();
        hubPath.setCreatedBy(userId);
        return hubPath;
    }

    public void update(UpdateHubPathRequest request, UUID userId) {
        this.distance = request.distance();
        this.duration = request.duration();
        this.setUpdatedBy(userId);
    }

    public void delete(UUID uuid) {
        this.isDeleted = true;
        this.setDeletedBy(uuid);
        this.setDeletedAt(LocalDateTime.now());
    }

}
