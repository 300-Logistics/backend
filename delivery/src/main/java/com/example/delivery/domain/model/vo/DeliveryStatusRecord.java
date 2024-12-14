package com.example.delivery.domain.model.vo;

import java.time.LocalDateTime;

import com.example.delivery.domain.model.enums.DeliveryStatus;
import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Embeddable
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryStatusRecord {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus deliveryStatus;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public static DeliveryStatusRecord of(DeliveryStatus deliveryStatus) {
		validate(deliveryStatus);
		return new DeliveryStatusRecord(deliveryStatus, LocalDateTime.now());
	}

	public DeliveryStatusRecord updateStatus(DeliveryStatus updatedDeliveryStatus) {
		validate(updatedDeliveryStatus);
		log.info("해당 상태로 변경 : parameterStatus={}", updatedDeliveryStatus);
		return new DeliveryStatusRecord(updatedDeliveryStatus, LocalDateTime.now());
	}

	private static void validate(DeliveryStatus deliveryStatus) {
		if (deliveryStatus == null) {
			throw new CustomException(ErrorCode.INVALID_DELIVERY_STATUS_NULL);
		}
	}

	@Override
	public String toString() {
		return "Status : " + deliveryStatus + ", updatedAt : " + updatedAt;
	}
}
