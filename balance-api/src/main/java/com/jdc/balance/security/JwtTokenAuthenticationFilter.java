package com.jdc.balance.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jdc.balance.security.JwtTokenProvider.Type;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtTokenProvider tokenProvider;
	
	private RequestMatcher securityRequestMatcher;
	
	@PostConstruct
	private void init() {
		securityRequestMatcher = new AntPathRequestMatcher("/security/**");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		if(!securityRequestMatcher.matches(request)) {
			var token = request.getHeader("Authorization");
			
			var authentication = tokenProvider.parse(token, Type.Access);
			
			if(null != authentication) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
