package com.example.auth.domain.model.entity;

import java.util.UUID;

import com.example.auth.domain.model.enums.DeliveryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_staff")
public class DeliveryStaff {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private UUID userId;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String slackId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryType deliveryType;

	private UUID hubId;

	public boolean isHubDeliveryStaff() {
		return this.deliveryType == DeliveryType.HUB_DELIVERY_STAFF;
	}

	public boolean isCompanyDeliveryStaff() {
		return this.deliveryType == DeliveryType.COMPANY_DELIVERY_STAFF;
	}

	@Builder
	public DeliveryStaff(UUID userId, String username, String slackId, DeliveryType deliveryType, UUID hubId) {
		this.userId = userId;
		this.username = username;
		this.slackId = slackId;
		this.deliveryType = deliveryType;
		this.hubId = hubId;
	}

	public void setDeleted() {
		this.deliveryType = DeliveryType.DEACTIVATE;
	}


}
