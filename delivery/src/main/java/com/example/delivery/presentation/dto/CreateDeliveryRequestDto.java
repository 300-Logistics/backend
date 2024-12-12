package com.example.delivery.presentation.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryRequestDto(
	@NotNull(message = "주문 ID는 필수입니다.")
	UUID orderId,
	@NotNull(message = "허브배송경로 조회를 위해 출발지 허브 ID는 필수입니다.")
	UUID startHubId,
	@NotNull(message = "허브배송경로 조회를 위해 도착지 허브 ID는 필수입니다.")
	UUID destinationHubId,
	@NotNull(message = "수령인 ID는 필수입니다.")
	UUID receiverId,
	@NotNull(message = "슬랙 메세지 발송을 위해 수령인 슬랙 ID는 필수입니다.")
	String receiverSlackId,
	String address

) {
}