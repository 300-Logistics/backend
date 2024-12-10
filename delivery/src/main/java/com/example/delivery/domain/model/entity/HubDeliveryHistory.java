package com.example.delivery.domain.model.entity;

import com.example.delivery.domain.model.vo.DistanceAndDuration;
import com.example.delivery.domain.model.vo.HubRoute;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_hub_delivery_history")
public class HubDeliveryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hubDeliveryHistoryId;

    @Embedded
    private HubRoute hubRoute;

    @Column(nullable = false)
    private int sequence;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "distance", column = @Column(name = "expected_distance", nullable = false)),
            @AttributeOverride(name = "duration", column = @Column(name = "expected_duration", nullable = false))
    })
    private DistanceAndDuration expectedDistanceAndDuration;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "distance", column = @Column(name = "distance", nullable = false)),
            @AttributeOverride(name = "duration", column = @Column(name = "duration", nullable = false))
    })
    private DistanceAndDuration distanceAndDuration;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private UUID deliveryId;

    @Builder
    public HubDeliveryHistory(HubRoute hubRoute, int sequence,
                              DistanceAndDuration expectedDistanceAndDuration,
                              DistanceAndDuration distanceAndDuration,
                              UUID deliveryId) {
        this.hubRoute = hubRoute;
        this.sequence = sequence;
        this.expectedDistanceAndDuration = expectedDistanceAndDuration;
        this.distanceAndDuration = distanceAndDuration;
        this.deliveryId = deliveryId;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }

}
