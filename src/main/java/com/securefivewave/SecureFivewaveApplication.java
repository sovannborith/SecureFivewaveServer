package com.securefivewave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecureFivewaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureFivewaveApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder {
		return new BCryptPasswordEncoder(STRENG);
	}
}
