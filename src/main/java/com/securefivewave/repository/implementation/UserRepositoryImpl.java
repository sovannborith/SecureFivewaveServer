package com.securefivewave.repository.implementation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.securefivewave.entity.Role;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserRole;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.enumeration.VerificationTypeEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.query.UserQuery;
import com.securefivewave.repository.IUserRoleRepository;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.repository.IUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements IUserRepository<User> {

	private final NamedParameterJdbcTemplate jdbc ;
	private final IRoleRepository<Role> roleRepository;
	private final IUserRoleRepository<UserRole> userRoleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User create(User user) {
		log.info("Adding new user...");		
		if(getEmailCount(user.getEmail().trim().toLowerCase())>0) throw new ApiException("Email already in use. Please use a different email and try again.");
		try
		{
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource params = getSqlParameterSouce(user);
			jdbc.update(UserQuery.INSERT_USER_QUERY, params, holder);
			user.setId(java.util.Objects.requireNonNull(holder.getKey().longValue()));
			user.setIsEnable(false);
			user.setIsLocked(false);
			addUserRole(user.getId(),VerificationTypeEnum.ACCOUNT.getType());
			String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),VerificationTypeEnum.ACCOUNT.getType());
			jdbc.update(UserQuery.INSERT_ACCOUNT_VERIFICATION_QUERY, Map.of("userId", user.getId(),"url", verificationUrl));
			//emailService.setVerificationUrl(user.getFirstName(),user.getEmail(),VerificationTypeEnum.ACCOUNT);
			return user;
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ApiException("No role " + RoleEnum.USER.toString() + " found");
		}
		catch(Exception exception) {
			throw new ApiException("An error occurred. Please try again.");
		}
	}

	@Override
	public Collection<User> list(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<User> getUserByEmail(String email){
		return null;//jdbc.query(UserQuery.GET_USER_BY_EMAIL_QUERY, Map.of("email_addr",email));
	}
	
	private Integer getEmailCount(String email)
	{
		return jdbc.queryForObject(UserQuery.COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);		
	}
	
	
	
	private SqlParameterSource getSqlParameterSouce(User user) {
		
		return new MapSqlParameterSource()
				.addValue("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName())
				.addValue("email", user.getEmail())
				.addValue("password",passwordEncoder.encode(user.getPassword()) );
	}
	private String getVerificationUrl(String key, String type) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
	}
	private void addUserRole(Long userId, String roleName) {
		
		UserRole ur =new UserRole();
		ur.setUserId(userId);
		ur.setRoleId(roleRepository.getRoleByRoleName(roleName).getId());
		ur.setIsActive(true);
		userRoleRepository.create(ur);
	}
}
