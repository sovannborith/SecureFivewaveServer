package com.securefivewave.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_DEFAULT)
@Data
public class Role {
	private Long id;
	@NotEmpty(message = "Role name cannot be empty")
	private String roleName;
	@NotEmpty(message = "Role description cannot be empty")
	private String roleDescription;
	
}
