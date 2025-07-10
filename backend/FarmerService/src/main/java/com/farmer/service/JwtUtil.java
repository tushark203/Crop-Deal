package com.farmer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY = "8k9pL2mQ7rT4vX5zY1cB3nF6hJ0wA8dE9gK2oP5sU7tW4xZ1yC3bN6fH8jM0qR5";
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String extractEmail(String token) {
    	 if (token == null) return null;
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject(); 
    }
    
    public String extractRole(String token) {
   	 if (token == null) return null;
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        String role = claims.get("role", String.class);
        return role;
   }
    
    public String getToken(HttpServletRequest request) {
    	 String authHeader = request.getHeader("Authorization");
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
             return authHeader.substring(7);
         }
         return null;
    }

}
