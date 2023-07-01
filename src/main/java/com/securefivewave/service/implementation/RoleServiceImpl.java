package com.securefivewave.service.implementation;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.securefivewave.dto.RoleDTO;
import com.securefivewave.dto.dtomapper.RoleDTOMapper;
import com.securefivewave.entity.Role;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.service.IRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    
    public RoleDTO createRole(Role role){
        log.info("Adding new role...");
		try
		{
			// Check if user with this role already existed then throw exception
			Role r = roleRepository.getRoleByRoleName(role.getRoleName());
			if(r !=null) {
				throw new InvalidDataAccessApiUsageException("User with this role already existed. Please use a different user or role and try again.");
			}
			roleRepository.save(role);

            return RoleDTOMapper.fromRole(role);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ApiException("No role " + RoleEnum.USER.toString() + " found");
		}
		catch(Exception exception) {
			throw exception;
		}
    }

    @Override
    public RoleDTO getRoleByRoleName(String roleName) {
        Role r =  roleRepository.getRoleByRoleName(roleName);

        if(r !=null)
        {
            return RoleDTOMapper.fromRole(r);
        }
        return null;
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role !=null){
            Role r = role.orElse(new Role());
            if(r !=null) return RoleDTOMapper.fromRole(r);
        }
        return null;
    }

    @Override
    public RoleDTO update(Role role) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    public String getRoleNameById(Long id)
    {
        Optional<Role> role =roleRepository.findById(id);
        if (role.isPresent())
        {
            return role.get().getRoleName();
        }
        return null;
    }

}
