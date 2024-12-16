package com.example.auth.infrastructure.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.auth.domain.model.entity.User;
import com.example.auth.domain.model.enums.UserRole;
import com.example.auth.libs.exception.CustomException;
import com.example.auth.libs.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final SecretKey secretKey;

	private final long expirationTime;

	public JwtProvider(
		@Value("${service.jwt.secret-key}") String secretKey,
		@Value("${service.jwt.access-expiration}") long expirationTime
	) {
		byte[] key = Decoders.BASE64.decode(secretKey);
		this.secretKey = Keys.hmacShaKeyFor(key);
		this.expirationTime = expirationTime;
	}

	public String generateToken(User user) {
		return Jwts.builder()
			.claim("userId", user.getId())
			.claim("username", user.getUsername())
			.claim("userRole", user.getUserRole())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(secretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims parseToken(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();

	}

	public boolean isMaster(String token) {
		try {
			Claims claims = parseToken(token);
			String userRole = claims.get("userRole", String.class);
			return UserRole.MASTER.name().equals(userRole);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}
