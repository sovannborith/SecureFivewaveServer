package com.securefivewave.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.service.user_token.UserTokenServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final UserTokenServiceImpl userTokenServiceImpl;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try{
            final String authHeader = request.getHeader(GlobalConstaint.AUTH_HEADER);
			
			if(authHeader ==null || !authHeader.startsWith(GlobalConstaint.JWT_PREFIX)) {
				
				return;
			}
			final String jwt = authHeader.substring(7);
            var accessToken = userTokenServiceImpl.getUserTokenByToken(jwt);
            if(accessToken !=null){
                userTokenServiceImpl.update(accessToken);
                SecurityContextHolder.clearContext();
            }
        }
        catch(Exception e){
            throw e;
        }
    }
    
}
