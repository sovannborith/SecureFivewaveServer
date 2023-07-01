package com.securefivewave.dto.dtomapper;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.securefivewave.dto.RoleDTO;
import com.securefivewave.entity.Role;

@Component
public class RoleDTOMapper {
	public static RoleDTO fromRole(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		BeanUtils.copyProperties(role, roleDTO);
		
		return roleDTO;
	}

	public static Optional<RoleDTO> fromRole(Optional<Role> role) {
		if(role.isPresent()) return fromRole(role);
		return null;
	}
	
	public static Role toRole(RoleDTO roleDTO) {
		Role role = new Role();
		BeanUtils.copyProperties(roleDTO,role);
		
		return role;
	}

	public static Optional<Role> toRole(Optional<RoleDTO> roleDTO) {
		if(roleDTO.isPresent()) return toRole(roleDTO);
		return null;
	}
}
