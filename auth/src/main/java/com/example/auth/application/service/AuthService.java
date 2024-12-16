package com.example.auth.application.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.domain.model.entity.User;
import com.example.auth.domain.model.vo.Username;
import com.example.auth.domain.repository.AuthRepository;
import com.example.auth.infrastructure.config.JwtProvider;
import com.example.auth.libs.exception.CustomException;
import com.example.auth.libs.exception.ErrorCode;
import com.example.auth.presentation.dto.SignInRequestDto;
import com.example.auth.presentation.dto.SignUpRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;


	@Transactional
	public void signUp(SignUpRequestDto requestDto) {
		String encodedPassword = passwordEncoder.encode(requestDto.password());
		User user = User.builder()
			.username(Username.of(requestDto.username()))
			.password(encodedPassword)
			.userRole(requestDto.userRole())
			.build();

		authRepository.save(user);
	}

	public String signIn(SignInRequestDto requestDto) {
		User user = getUser(requestDto);

		if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
		}
		return jwtProvider.generateToken(user);
	}

	public Boolean validateToken(String token) {
		validateTokenFormat(token);
		String accessToken = token.substring(7);

		if (!jwtProvider.validateToken(accessToken)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
		return true;
	}

	public Boolean validateMasterToken(String token) {
		validateTokenFormat(token);
		String accessToken = token.substring(7);

		if (!jwtProvider.isMaster(accessToken)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
		return true;
	}

	private void validateTokenFormat(String token) {
		if (token == null || !token.startsWith("Bearer ")) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	private User getUser(UUID targetUserId) {
		return authRepository.findById(targetUserId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	private User getUser(SignInRequestDto requestDto) {
		Username username = Username.of(requestDto.username());
		return authRepository.findByUsername(username)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
