package com.securefivewave.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
	
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	
		
	public String generateToken( String email) {
		return generateToken(new HashMap<>(), email);
	}
	public String generateToken(Map<String,Object> extraClaim, String email) {
		return generateToken(new HashMap<>(), email, jwtExpiration);
	}
	public String generateToken(Map<String,Object> extraClaim, String email,long expiration) {
		return buildToken(extraClaim, email,expiration);
	}

	public String generateRefreshToken(String email) {
		return buildToken(new HashMap<>(), email,refreshExpiration);
	}

	private String buildToken(Map<String,Object> extraClaim, String email,long expiration){
		return Jwts
				.builder() 
				.setClaims(extraClaim)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ expiration))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, String email) {
		final String userName =extractUsername(token);
		return userName.toLowerCase().equals(email.toLowerCase()) && !isTokenExpired(token);
	}

	public Date getJwtExpiryDate(String token){
		return extractExpiration(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public<T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes =Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
