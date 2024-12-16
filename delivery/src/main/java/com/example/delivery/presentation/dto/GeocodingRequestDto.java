package com.example.delivery.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record GeocodingRequestDto(
	@NotBlank(message = "주소는 필수입니다.")
	@Pattern(
		regexp = "^([가-힣]+(특별시|광역시|도))\\s([가-힣]+(시|군|구))\\s(.+)$",
		message = "주소 입력 양식은 다음과 같습니다. 예: 서울특별시 강남구 선릉로 100길 1"
	)
	String address
) {
}
