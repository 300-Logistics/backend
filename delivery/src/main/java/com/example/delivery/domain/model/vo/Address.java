package com.example.delivery.domain.model.vo;

import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

	@Column(nullable = false)
	private String fullAddress;

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;

	public static Address of(String fullAddress, double latitude, double longitude) {
		validateAddress(fullAddress, latitude, longitude);
		return new Address(fullAddress, latitude, longitude);
	}

	private static void validateAddress(String fullAddress, double latitude, double longitude) {
		if (fullAddress == null || fullAddress.isBlank()) {
			throw new CustomException(ErrorCode.INVALID_ADDRESS_EMPTY_OR_NULL);
		}
		if (latitude < 33.0 || latitude > 38.5) {
			throw new CustomException(ErrorCode.INVALID_LATITUDE);
		}
		if (longitude < 124.0 || longitude > 132.0) {
			throw new CustomException(ErrorCode.INVALID_LONGITUDE);
		}
	}
}
