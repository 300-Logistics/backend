package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.gateway.config.JwtProvider;
import com.example.gateway.libs.exception.CustomException;
import com.example.gateway.libs.exception.ErrorCode;

import reactor.core.publisher.Mono;

@Component
public class CustomAuthorizationFilter implements GlobalFilter, Ordered {

	private final JwtProvider jwtProvider;

	public CustomAuthorizationFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();


		if (path.contains("/api/auth/signUp") || path.contains("/api/auth/signIn") || path.contains("/api/delivery-staffs/register")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		String token = authHeader.substring(7);
		if (!jwtProvider.validateToken(token)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}
}
