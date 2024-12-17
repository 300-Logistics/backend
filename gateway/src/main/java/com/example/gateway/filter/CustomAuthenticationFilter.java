package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.gateway.config.JwtProvider;
import com.example.gateway.libs.exception.CustomException;
import com.example.gateway.libs.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationFilter implements GlobalFilter, Ordered {

	private final JwtProvider jwtProvider;

	public CustomAuthenticationFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String path = exchange.getRequest().getURI().getPath();
		if (path.contains("/api/auth/signUp") || path.contains("/api/auth/signIn") || path.contains("/api/delivery-staff/register")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		String token = authHeader.substring(7);

		if (!jwtProvider.validateToken(token)) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		Claims claims = jwtProvider.parseToken(token);
		String userId = claims.get("userId", String.class);
		String userRole = claims.get("userRole", String.class);

		ServerHttpRequest httpRequest = exchange.getRequest().mutate()
			.header("X-User-Id", userId)
			.header("X-User-Role", userRole)
			.build();
		return chain.filter(exchange.mutate().request(httpRequest).build());
	}


	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
