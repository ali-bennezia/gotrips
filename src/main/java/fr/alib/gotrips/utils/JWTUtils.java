package fr.alib.gotrips.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@PropertySource("classpath:credentials.properties")
@Component
@ConfigurationProperties(prefix="jwt")
@ConfigurationPropertiesScan
public class JWTUtils {
	private String secretKey;
	private final long expirationTime = 864_000_000;
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSigningKey())
				.compact();
	}
	
	public String extractUsername(String token) {
		try {
			return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (JwtException jwtEx) {
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
}
