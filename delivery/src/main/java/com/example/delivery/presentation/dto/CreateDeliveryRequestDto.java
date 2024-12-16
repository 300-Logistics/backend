package com.example.delivery.presentation.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateDeliveryRequestDto(
	@NotBlank(message = "주문 ID는 필수입니다.")
	UUID orderId,
	@NotBlank(message = "허브배송경로 조회를 위해 출발지 허브 ID는 필수입니다.")
	UUID startHubId,
	@NotBlank(message = "허브배송경로 조회를 위해 도착지 허브 ID는 필수입니다.")
	UUID destinationHubId,
	@NotBlank(message = "수령인 ID는 필수입니다.")
	UUID receiverId,
	@NotBlank(message = "슬랙 메세지 발송을 위해 수령인 슬랙 ID는 필수입니다.")
	String receiverSlackId,
	@NotBlank(message = "주소는 필수입니다.")
	@Pattern(
		regexp = "^([가-힣]+(특별시|광역시|도))\\s([가-힣]+(시|군|구))\\s(.+)$",
		message = "주소 입력 양식은 다음과 같습니다. 예: 서울특별시 강남구 선릉로 100길 1"
	)
	String address

) {
}