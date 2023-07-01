package com.securefivewave.dto.dtomapper;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.entity.UserRole;

@Component
public class UserRoleDTOMapper {
	public static UserRoleDTO fromUserRole(UserRole userRole) {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		BeanUtils.copyProperties(userRole, userRoleDTO);
		
		return userRoleDTO;
	}

	public static Optional<UserRoleDTO> fromUserRole(Optional<UserRole> userRole) {
		if(userRole.isPresent()) return fromUserRole(userRole);
		return null;
	}
	
	public static UserRole toUserRole(UserRoleDTO userRoleDTO) {
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(userRoleDTO,userRole);
		
		return userRole;
	}

	public static Optional<UserRole> toUserRole(Optional<UserRoleDTO> userRoleDTO) {
		if(userRoleDTO.isPresent()) return toUserRole(userRoleDTO);
		return null;
	}
}
