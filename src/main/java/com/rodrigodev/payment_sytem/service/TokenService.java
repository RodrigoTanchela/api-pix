package com.rodrigodev.payment_sytem.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rodrigodev.payment_sytem.entity.User;

@Service
public class TokenService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().withIssuer("auth")
					.withSubject(user.getEmail()).withExpiresAt(ExpirationDate())
					.sign(algorithm);
			return token;
					
		}catch (Exception exception) {
			throw new RuntimeException("ERRO: Token não foi gerado", exception);
		}

	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("auth")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			throw new RuntimeException("Token inválido");
		}
	}

	private Date ExpirationDate() {
        return Date.from(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")));
    }
}
