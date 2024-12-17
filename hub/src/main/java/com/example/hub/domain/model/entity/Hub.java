package com.example.hub.domain.model.entity;

import com.example.hub.presentation.dto.request.HubRequest;
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
    name = "p_hub"
)
public class Hub extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static Hub create(HubRequest request, UUID userId) {
        Hub hub = Hub.builder()
            .name(request.name())
            .address(request.address())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .isDeleted(false)
            .build();
        hub.setCreatedBy(userId);
        return hub;
    }

    public void update(HubRequest request, UUID userId) {
        this.name = request.name();
        this.address = request.address();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
        this.setUpdatedBy(userId);
    }

    public void delete(UUID uuid) {
        this.isDeleted = true;
        this.setDeletedBy(uuid);
        this.setDeletedAt(LocalDateTime.now());
    }

}
