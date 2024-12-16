package com.example.auth.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.application.service.AuthService;
import com.example.auth.presentation.dto.SignInRequestDto;
import com.example.auth.presentation.dto.SignUpRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(
		@Valid @RequestBody SignUpRequestDto requestDto
	) {
		authService.signUp(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 완료");
	}

	@PostMapping("/signIn")
	public ResponseEntity<String> signIn(
		@RequestBody SignInRequestDto requestDto
	) {
		String token = authService.signIn(requestDto);
		return ResponseEntity.ok()
			.header("Authorization", "Bearer " + token)
			.body("로그인 성공");
	}

	@PostMapping("/validate")
	public ResponseEntity<Boolean> validateToken(
		@RequestHeader("Authorization") String token
	) {
		return ResponseEntity.ok(authService.validateToken(token));
	}

	@PostMapping("/validate/master")
	public ResponseEntity<Boolean> validateMasterToken(
		@RequestHeader("Authorization") String token
	) {
		return ResponseEntity.ok(authService.validateMasterToken(token));
	}
}