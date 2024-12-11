package com.example.delivery.domain.model.entity;

import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.domain.model.vo.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "p_delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private boolean isDeleted = false;


    /**
     * HubRoute 값객체로 바꿔야한다 -> 여기서 허브 경로 데이터를 관리해야함
     * 안바꿔도 된다 -> 허브경로 정보를 여기서는 단순참조만 한다.
     * 후자 선택
     */
    private UUID startHubId;
    private UUID destinationHubId;

    private UUID deliveryStaffId;
    private UUID receiverId;

    private UUID orderId;

    private UUID hubDeliveryHistoryId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "company_delivery_history_id", referencedColumnName = "deliveryHistoryId")
    private CompanyDeliveryHistory companyDeliveryHistory;

    @Builder
    public Delivery(DeliveryStatus deliveryStatus, Address address,
                    UUID startHubId, UUID destinationHubId, UUID deliveryStaffId,
                    UUID receiverId, UUID orderId, UUID hubDeliveryHistoryId,
                    CompanyDeliveryHistory companyDeliveryHistory) {
        this.deliveryStatus = deliveryStatus;
        this.address = address;
        this.startHubId = startHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryStaffId = deliveryStaffId;
        this.orderId = orderId;
        this.hubDeliveryHistoryId = hubDeliveryHistoryId;
        this.companyDeliveryHistory = companyDeliveryHistory;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }
}
