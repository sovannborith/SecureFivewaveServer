/**
 * 
 */
package com.securefivewave.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.entity.HttpResponse;
import com.securefivewave.entity.User;
import com.securefivewave.service.IUserService;

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
	
	@PostMapping("/register")
	public ResponseEntity<HttpResponse> saveUser (@RequestBody @Valid User user){
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
	private URI getUri() {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>" ).toString());
	}
}
