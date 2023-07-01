package com.securefivewave.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.dto.dtomapper.UserRoleDTOMapper;
import com.securefivewave.entity.UserRole;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.repository.IUserRoleRepository;
import com.securefivewave.service.IUserRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements IUserRoleService{

    private final IUserRoleRepository userRoleRepository;

    @Override
    public UserRoleDTO createUserRole(UserRole userRole) {
        log.info("Adding new user role...");
        try
		{
			// Check if user with this role already existed then throw exception
			UserRole ur = userRoleRepository.getUserRoleByUserIdRoleId(userRole.getUserId(),userRole.getRoleId());
			if(ur !=null) {
				throw new InvalidDataAccessApiUsageException("User with this role already existed. Please use a different user or role and try again.");
			}
			userRoleRepository.save(userRole);

            return UserRoleDTOMapper.fromUserRole(userRole);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ApiException("No role " + RoleEnum.USER.toString() + " found");
		}
		catch(Exception exception) {
			throw exception;
		}
    }

    @Override
    public UserRoleDTO getUserRoleByUserIdRoleId(Long userId, Long roleId) {
        UserRole ur =  userRoleRepository.getUserRoleByUserIdRoleId(userId,roleId);

        if(ur !=null)
        {
            return UserRoleDTOMapper.fromUserRole(ur);
        }
        return null;
    }

    @Override
    public UserRoleDTO getUserRoleById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserRoleById'");
    }

    @Override
    public UserRoleDTO update(UserRole role) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<UserRoleDTO> getUserRoleByUserId(Long userId) {
        List<UserRole> userRoles =userRoleRepository.getUserRoleByUserId(userId);
        if(userRoles.isEmpty()){
            return null;
        }

        List<UserRoleDTO> userRoleDTO =new ArrayList<>();
        for(UserRole userRole : userRoles )
        {
            userRoleDTO.add( UserRoleDTOMapper.fromUserRole(userRole));
        }
        
        return userRoleDTO;
    }
}
