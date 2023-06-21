package com.securefivewave.repository.implementation;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.securefivewave.domain.UserRole;
import com.securefivewave.exception.ApiException;
import com.securefivewave.query.UserRoleQuery;
import com.securefivewave.repository.UserRoleRepository;
import com.securefivewave.rowmapper.UserRoleRowMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j

public class UserRoleRepositoryImp implements UserRoleRepository<UserRole> {

	
	private final NamedParameterJdbcTemplate jdbc ;
	
	@Override
	public UserRole create(UserRole userRole) {
		log.info("Adding new User Role...");		
		
		try
		{
			UserRole ur = getUserRoleByUserIdRoleId(Long.valueOf(userRole.getUserId()), Long.valueOf(userRole.getRoleId()));
			if(ur != null) {
				throw new ApiException("User with this role already existed. Please use a different user or role and try again.");
			}
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource params = getSqlParameterSouce(userRole);
			jdbc.update(UserRoleQuery.INSERT_USER_ROLE_QUERY, params, holder);
			userRole.setId(java.util.Objects.requireNonNull(holder.getKey().longValue()));
			userRole.setIsActive(true);
			return userRole;
		}
		
		catch(Exception exception) {
			throw new ApiException("An error occurred. Please try again.");
		}
	}

	@Override
	public List<UserRole> list(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole update(UserRole data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getUserRoleByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getUserRoleByUserIdRoleId(Long userId, Long roleId) {
		return jdbc.queryForObject(UserRoleQuery.GET_USER_ROLE_BY_USERID_ROLEID_QUERY, Map.of("userId", userId, "roleId", roleId),new UserRoleRowMapper());
		
	}
	private SqlParameterSource getSqlParameterSouce(UserRole userRole) {
		// TODO Auto-generated method stub
		return null;
	}
}
