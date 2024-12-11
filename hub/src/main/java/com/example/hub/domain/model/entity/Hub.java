package com.example.hub.domain.model.entity;

import com.example.hub.dto.request.HubRequest;
import jakarta.persistence.*;
import lombok.*;

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

    public static Hub create(HubRequest request) {
        return Hub.builder()
            .name(request.name())
            .address(request.address())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .isDeleted(false)
            .build();
    }

}
