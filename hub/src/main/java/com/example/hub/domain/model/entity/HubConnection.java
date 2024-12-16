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
    name = "p_hub_connection"
)
public class HubConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub; // 시작 허브

    @ManyToOne
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub; // 도착 허브

    @Column(name = "distance", nullable = false)
    private double distance; // 이동 거리 (km)

    @Column(name = "duration", nullable = false)
    private int duration; // 소요 시간 (분)
}
