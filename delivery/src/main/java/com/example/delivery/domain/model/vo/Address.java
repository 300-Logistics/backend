package com.example.delivery.domain.model.vo;

import com.example.delivery.libs.exception.CustomException;
import com.example.delivery.libs.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@Column(nullable = false)
	private String fullAddress;

	private Address(String fullAddress) {
		validateAddress(fullAddress);
		this.fullAddress = fullAddress.trim();
	}

	public static Address of(String fullAddress) {
		return new Address(fullAddress);
	}

	private static void validateAddress(String fullAddress) {
		if (fullAddress == null || fullAddress.isBlank()) {
			throw new CustomException(ErrorCode.INVALID_ADDRESS_EMPTY_OR_NULL);
		}
	}
}
