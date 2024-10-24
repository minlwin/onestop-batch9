package com.jdc.balance.security;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jdc.balance.model.utils.ApplicationTokenProperties;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	
	private final ApplicationTokenProperties tokenProperties;
	private final SecretKey key = Jwts.SIG.HS512.key().build();
	
	public enum Type {
		Access, Refresh
	}

	public Authentication parse(String token) {
		
		try {
			if(StringUtils.hasLength(token)) {
				var jwt = Jwts.parser()
						.requireIssuer(tokenProperties.getIssuer())
						.verifyWith(key)
						.build()
						.parseSignedClaims(token);
				
				var type = jwt.getPayload().get("type");
				
				if(null == type || !type.equals(Type.Access.name())) {
					throw new JwtTokenInvalidateException("Invalid token type.", null);
				}
				
				var username = jwt.getPayload().getSubject();
				var authorities = Arrays.stream(jwt.getPayload().get("role").toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.toList();
				
				return UsernamePasswordAuthenticationToken.authenticated(username, null, authorities);
			}
		} catch (ExpiredJwtException e) {
			throw new JwtTokenExpirationException("Access token is expired. Please refresh token again.", e);
		} catch (JwtException e) {
			throw new JwtTokenInvalidateException(e.getMessage(), e);
		}
		
		return null;
	}

	public String generate(Authentication authentication, Type type) {
 		
		Date issueAt = new Date();
		var calendar = Calendar.getInstance();
		calendar.setTime(issueAt);
		calendar.add(Calendar.MINUTE, type == Type.Access ? tokenProperties.getLife().getAccess() : 
			tokenProperties.getLife().getRefresh());
		
		Date expireAt = calendar.getTime();
		var authorities = authentication.getAuthorities()
				.stream()
				.map(a -> a.getAuthority())
				.collect(Collectors.joining(","));
		
		return Jwts.builder()
				.subject(authentication.getName())
				.issuer(tokenProperties.getIssuer())
				.issuedAt(issueAt)
				.expiration(expireAt)
				.signWith(key)
				.claim("type", type)
				.claim("role", authorities)
				.compact();
	}

}
