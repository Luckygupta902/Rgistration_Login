package com.lucky.autobg1.JwtUtil;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lucky.autobg1.userregistrationmodle.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	// Generate a Key instance from the secret
	 public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, username);
	    }

	    // Method to create the token with custom claims and subject
	    private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + expiration))
	                .signWith(SignatureAlgorithm.HS256, secret)
	                .compact();
	    }

	    // Method to validate a token
	    public Boolean validateToken(String token, String username) {
	        final String tokenUsername = extractUsername(token);
	        return (tokenUsername.equals(username) && !isTokenExpired(token));
	    }

	    // Extract username from token
	    public String extractUsername(String token) {
	        return extractClaims(token).getSubject();
	    }

	    // Check if token has expired
	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    // Extract expiration date from token
	    private Date extractExpiration(String token) {
	        return extractClaims(token).getExpiration();
	    }

	    // Method to extract claims from token
	    private Claims extractClaims(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

}
