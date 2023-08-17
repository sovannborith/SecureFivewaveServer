/**
 * 
 */
package com.securefivewave.controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.securefivewave.dto.usertoken.UserTokenRequest;
import com.securefivewave.dto.usertoken.UserTokenResponse;
import com.securefivewave.handler.response.CommonResponse;
import com.securefivewave.jwt.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/admin/usertoken")
@RequiredArgsConstructor
public class UserTokenController {

	private final JwtService jwtService;
	
	@PostMapping("/getTokenDetail")
	public ResponseEntity<CommonResponse<UserTokenResponse>> getTokenDetails (@RequestBody @Valid UserTokenRequest request) throws Exception{
		UserTokenResponse u =new UserTokenResponse();
		u.setUserName(jwtService.extractUsername(request.getToken()));
		u.setExpiryDate(jwtService.getJwtExpiryDate(request.getToken()));
		try{
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(u));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}
}
