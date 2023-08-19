/**
 * 
 */
package com.securefivewave.controller;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.securefivewave.dto.UserDTO;
import com.securefivewave.dto.user.ChangePasswordRequest;
import com.securefivewave.dto.user.ChangePasswordResponse;
import com.securefivewave.entity.User;
import com.securefivewave.handler.request.RegisterRequest;
import com.securefivewave.handler.response.CommonResponse;
/* import com.securefivewave.jwt.JwtService;
import com.securefivewave.service.implementation.UserEventServiceImpl;
import com.securefivewave.service.implementation.UserOtpServiceImpl; */
import com.securefivewave.service.implementation.UserServiceImpl;
//import com.securefivewave.service.implementation.UserTokenServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/admin/user")
public class UserController {

	private final UserServiceImpl userServiceImpl;
	/* private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final UserOtpServiceImpl userOtpServiceImpl;
	private final UserTokenServiceImpl userTokenServiceImpl;
	private final AuthenticationManager authenticationManager;
	private final UserEventServiceImpl userEventServiceImpl; */

	
	@PostMapping("/create")
	public ResponseEntity<CommonResponse<UserDTO>> create (@RequestBody @Valid RegisterRequest request) throws Exception{
		User user =new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		try{
			UserDTO res = userServiceImpl.createUser(user);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}
	@GetMapping("/getAllUsers")
	public ResponseEntity<CommonResponse<List<UserDTO>>> getAllUsers(){
		
		try{
			List<UserDTO> res = userServiceImpl.getAllUsers();
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}

	@PostMapping("/changePassword")
	public ResponseEntity<CommonResponse<ChangePasswordResponse>> changePassword(@RequestBody @Valid ChangePasswordRequest request){
		try{
			ChangePasswordResponse res = userServiceImpl.changeUserPassword(request.getEmail(), request.getOldPassword(),request.getNewPassword());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}
	
}
