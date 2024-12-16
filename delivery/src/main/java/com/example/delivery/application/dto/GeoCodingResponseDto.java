package com.example.delivery.application.dto;

import java.util.List;

public record GeoCodingResponseDto(
	List<AddressInfo> addressInfoList

) {
	public record AddressInfo(
		String roadAddress,
		String originalAddress,
		double x,
		double y
	){}
}
