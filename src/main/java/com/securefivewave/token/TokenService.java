package com.securefivewave.token;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securefivewave.auth.AuthenticationResponse;
import com.securefivewave.auth.service.SecureFivewaveUserDetail;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.User;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.implementation.RoleServiceImpl;
import com.securefivewave.service.implementation.UserRoleServiceImpl;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    
    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final UserRoleServiceImpl userRoleServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException, StreamWriteException, DatabindException, java.io.IOException {

        final String authHeader = request.getHeader(GlobalConstaint.AUTH_HEADER);
		final String refreshToken;
		final String userEmail;
		if(authHeader ==null || !authHeader.startsWith(GlobalConstaint.JWT_PREFIX)) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		log.info(userEmail);
		if(userEmail !=null) {
            User user = userRepository.getUserByEmail(userEmail);
			SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user,userRoleServiceImpl,roleServiceImpl );
            
			if(jwtService.isTokenValid(refreshToken, userDetails)) {
				String accessToken = jwtService.generateToken(userDetails);
                var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreschToken(refreshToken)
                    .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
    }


}
