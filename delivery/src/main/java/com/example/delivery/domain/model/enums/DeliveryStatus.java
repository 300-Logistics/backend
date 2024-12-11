package com.example.delivery.domain.model.enums;

import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

public enum DeliveryStatus {

	WAITING_IN_START_HUB,
	MOVING_TO_DESTINATION_HUB,
	ARRIVED_AT_DESTINATION_HUB,

	BEFORE_DELIVERY_START,
	DELIVERY_IN_PROGRESS,
	DELIVERY_COMPLETED,

	DELIVERY_CANCELLED;

	public boolean canChangeStatus(DeliveryStatus nextStatus) {
		switch (this) {
			case WAITING_IN_START_HUB -> {
				return nextStatus == MOVING_TO_DESTINATION_HUB;
			}
			case MOVING_TO_DESTINATION_HUB -> {
				return nextStatus == ARRIVED_AT_DESTINATION_HUB;
			}
			case ARRIVED_AT_DESTINATION_HUB -> {
				return nextStatus == BEFORE_DELIVERY_START || nextStatus == DELIVERY_CANCELLED;
			}
			case BEFORE_DELIVERY_START -> {
				return nextStatus == DELIVERY_IN_PROGRESS || nextStatus == DELIVERY_CANCELLED;
			}
			case DELIVERY_IN_PROGRESS -> {
				return nextStatus == DELIVERY_COMPLETED || nextStatus == DELIVERY_CANCELLED;
			}
			default -> {
				throw new CustomException(ErrorCode.INVALID_DELIVERY_STATUS_CHANGE);
			}
		}
	}
}
