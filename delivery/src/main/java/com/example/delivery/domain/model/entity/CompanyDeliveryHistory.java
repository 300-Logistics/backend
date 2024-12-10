package com.example.delivery.domain.model.entity;

import com.example.delivery.domain.model.vo.Address;
import com.example.delivery.domain.model.vo.DistanceAndDuration;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "p_company_delivery_history")
public class CompanyDeliveryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deliveryHistoryId;

    @Embedded
    private Address address;

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

    @Column(nullable = false)
    private UUID deliveryStaffId;

    @OneToOne(mappedBy = "companyDeliveryHistory")
    private Delivery delivery;

    @Builder
    public CompanyDeliveryHistory(Address address, DistanceAndDuration expectedDistanceAndDuration,
                                  DistanceAndDuration distanceAndDuration, UUID deliveryStaffId,
                                  Delivery delivery) {
        this.address = address;
        this.expectedDistanceAndDuration = expectedDistanceAndDuration;
        this.distanceAndDuration = distanceAndDuration;
        this.deliveryStaffId = deliveryStaffId;
        this.delivery = delivery;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }
}
