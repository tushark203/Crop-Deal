package com.user.filter;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter {

	private final String SECRET_KEY = "8k9pL2mQ7rT4vX5zY1cB3nF6hJ0wA8dE9gK2oP5sU7tW4xZ1yC3bN6fH8jM0qR5";
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		
		String path = request.getURI().getPath();
		if (path.contains("/user/login") || path.contains("/farmer-register") || path.contains("/dealer-register")|| path.contains("/crop") || path.contains("/crop/allCrops") || path.matches("^/crop/\\d+$" )|| path.matches("^/crop/farmer-det/\\d+$" )) {
			return chain.filter(exchange); 
		}

		String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (token == null || !token.startsWith("Bearer ")) {
			return onError(exchange, "Missing or invalid Authorization header.Login First", HttpStatus.UNAUTHORIZED);
		}

		String jwt = token.substring(7);
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

		String role = claims.get("role", String.class);

		if (!isAuthorized(path, role)) {
			return onError(exchange, "Not authorized,You have not access", HttpStatus.FORBIDDEN);
		}

		return chain.filter(exchange);
	}

	private boolean isAuthorized(String path, String role) {
		if (path.startsWith("/farmer") && role.equals("FARMER"))
			return true;
		if (path.startsWith("/dealer") && role.equals("DEALER"))
			return true;
		if (path.startsWith("/admin") && role.equals("ADMIN")) {
			return true;
		}
			
		return false;
	}

//	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
//		ServerHttpResponse response = exchange.getResponse();
//		response.setStatusCode(status);
//		return response.setComplete();
//	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
	    ServerHttpResponse response = exchange.getResponse();
	    response.setStatusCode(httpStatus);

	    // Set response headers
	    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

	    // Create a response body with the error message
	    String errorJson = String.format("%s", err);
	    DataBuffer buffer = response.bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));

	    return response.writeWith(Mono.just(buffer));
	}

}
