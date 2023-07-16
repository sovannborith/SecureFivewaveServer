/**
 * 
 */
package com.securefivewave.controller.user;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.User;
import com.securefivewave.handler.HttpResponse;
import com.securefivewave.service.IUserService;
import com.securefivewave.service.implementation.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

	private final IUserService userService;
	private final UserServiceImpl userServiceImpl;
	
	@PostMapping("/create")
	public ResponseEntity<HttpResponse> saveUser (@RequestBody @Valid User user) throws Exception{
		UserDTO userDTO = userService.createUser(user);
		return ResponseEntity.created(getUri()).body(
				HttpResponse.builder()
				.timeStamp(java.time.LocalDateTime.now().toString())
				.data(Map.of("user", userDTO))
				.message("User created")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
				);
	}
	@GetMapping("/list")
	public List<UserDTO> getAllUsers(){
		return userServiceImpl.getAllUsers();
	}
	private URI getUri() {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>" ).toString());
	}
}
