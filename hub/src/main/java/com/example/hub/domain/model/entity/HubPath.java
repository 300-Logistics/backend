package com.example.hub.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "p_hub_path"
)
public class HubPath {

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

}
