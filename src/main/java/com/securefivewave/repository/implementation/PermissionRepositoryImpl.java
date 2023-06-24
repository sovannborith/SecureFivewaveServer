package com.securefivewave.repository.implementation;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.securefivewave.repository.IPermissionRepository;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.entity.Permission;
import com.securefivewave.entity.Role;
import com.securefivewave.query.UserRoleQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PermissionRepositoryImpl implements IPermissionRepository<Permission> 

{
	
	private final NamedParameterJdbcTemplate jdbc ;
	
	@Override	
	public Permission create(Permission permission) {
		log.info("Adding new permission...");
		// TODO Auto-generated method stub
		try
		{
			// Check if user with this role already existed then throw exception
			/*
			Role r = getRoleByRoleName(role.getRoleName());
			if(r != null) {
				throw new InvalidDataAccessApiUsageException("User with this role already existed. Please use a different user or role and try again.");
			}
			
			// Save new user role
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource params = getSqlParameterSouce(role);
			jdbc.update(UserRoleQuery.INSERT_USER_ROLE_QUERY, params, holder);
			*/
			//userRole.setId(java.util.Objects.requireNonNull(holder.getKey().longValue()));
		}
		catch(EmptyResultDataAccessException exception) {
			
		}
		catch(Exception exception) {
			
		}
		// Send verification Url
		// Save Url in verification table
		// Send email to user with verification url
		// Return the newly created user
		// If any error, throw exception with proper message
		return null;
	}

	@Override
	public List<Permission> list(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	private SqlParameterSource getSqlParameterSouce(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission update(Permission data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermissionByRoleIdObjectId(Long roleId, Long objectId) {
		// TODO Auto-generated method stub
		return null;
	}
}
