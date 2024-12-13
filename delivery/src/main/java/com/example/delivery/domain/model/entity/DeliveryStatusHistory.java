package com.example.delivery.domain.model.entity;

import java.time.LocalDateTime;

import com.example.delivery.domain.model.enums.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus deliveryStatus;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id", nullable = false)
	private Delivery delivery;

	@Builder
	private DeliveryStatusHistory(DeliveryStatus deliveryStatus, Delivery delivery) {
		this.deliveryStatus = deliveryStatus;
		this.updatedAt = LocalDateTime.now();
		this.delivery = delivery;
	}

	public static DeliveryStatusHistory of(DeliveryStatus deliveryStatus, Delivery delivery) {
		return DeliveryStatusHistory.builder()
			.deliveryStatus(deliveryStatus)
			.delivery(delivery)
			.build();
	}
}
