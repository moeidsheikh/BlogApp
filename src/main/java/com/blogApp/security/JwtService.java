package com.blogApp.security;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET = "F2311CFCA02CF75545EB569AEB0CE52A422BABE34355B6480E68D8C12AD7D6307904A800BCD7C19A201AA4E01FCA65126FC05DDE5C0C9C3C960DC96F50BA9D64";
	private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);
	
	public String generateToken(UserDetails userDetails) {
		
		Map<String, String> claims = new HashMap<>();
		claims.put("name","moeid");
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(decodedKey) ;
	}

	public Claims getClaims(String jwt) {
		
		Claims claims = Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
		return claims;
	}
	
	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}
	
	public boolean isTokenValid(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}
	
}
