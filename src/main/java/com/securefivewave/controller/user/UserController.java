/**
 * 
 */
package com.securefivewave.controller.user;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.securefivewave.dto.UserDTO;
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
	@GetMapping("/list")
	public List<UserDTO> getAllUsers(){
		return userServiceImpl.getAllUsers();
	}
	/* private URI getUri() {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>" ).toString());
	} */
}
