package com.securefivewave.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.securefivewave.auth.service.SecureFivewaveUserDetailService;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.service.implementation.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final SecureFivewaveUserDetailService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
				
		try{

			if(request.getRequestURI().toLowerCase().contains("login") || request.getRequestURI().toLowerCase().contains("register") || request.getRequestURI().toLowerCase().contains("resendotp")  || request.getRequestURI().toLowerCase().contains("verifyotp"))
			{
				filterChain.doFilter(request, response);
				return;
			}

			final String authHeader = request.getHeader(GlobalConstaint.AUTH_HEADER);
			final String jwt;
			final String userEmail;
			if(authHeader ==null || authHeader =="" || !authHeader.startsWith(GlobalConstaint.JWT_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			//request.getRequestURL().indexOf(userEmail, 0)
			jwt = authHeader.substring(7);
			/*
			var isTokenExpired = jwtService.getJwtExpiryDate(jwt);
			Date date = new Date();
			 if(isTokenExpired.before(date))
			{
				//Renew token
				
			} */
			userEmail = jwtService.extractUsername(jwt);
			if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
			
				if(jwtService.isTokenValid(jwt, userDetails.getUsername())) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
}
