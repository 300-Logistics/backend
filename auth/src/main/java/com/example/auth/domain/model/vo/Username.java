package com.example.auth.domain.model.vo;

import com.example.auth.libs.exception.CustomException;
import com.example.auth.libs.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

	@Column(name = "username", nullable = false)
	private String username;

	private Username(String username) {
		validateUsername(username);
		this.username = username;
	}

	public static Username of(String username) {
		return new Username(username);
	}

	private void validateUsername(String username) {
		if (username == null || username.isBlank()) {
			throw new CustomException(ErrorCode.INVALID_USERNAME);
		}
		if (username.length() < 3 || username.length() > 15) {
			throw new CustomException(ErrorCode.INVALID_USERNAME_LENGTH);
		}
		if (!username.matches("^[a-z0-9]+$")) {
			throw new CustomException(ErrorCode.INVALID_USERNAME_TYPE);
		}
	}
}