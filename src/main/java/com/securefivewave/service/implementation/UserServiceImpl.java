
package com.securefivewave.service.implementation;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.securefivewave.dto.UserDTO;
import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.dto.dtomapper.UserDTOMapper;
import com.securefivewave.entity.Role;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserRole;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService{
	
	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final UserRoleServiceImpl userRoleServiceImpl;


	@Override
	public UserDTO createUser(User user) {
		log.info("Adding new user...");
		
		if(getUserByEmail(user.getEmail().trim().toLowerCase())!=null) throw new ApiException("Email already in use. Please use a different email and try again.");
		try
		{
			user.setIsEnable(false);
			user.setIsLocked(false);
			userRepository.save(user);
			
			// Check if USER role is available in the DB
			Role role = roleRepository.getRoleByRoleName(RoleEnum.USER.name());
			if(role !=null) {
				// Check if this user has USER role assign, if not add otherwise skip
				UserRoleDTO ur = userRoleServiceImpl.getUserRoleByUserIdRoleId(user.getId(), role.getId());
				if(ur ==null)
				{
					log.info("Adding user to the role: " + RoleEnum.USER.name());
					UserRole userRole =new UserRole();
					userRole.setUserId(user.getId());
					userRole.setRoleId(role.getId());
					userRole.setIsActive(true);
					userRoleServiceImpl.createUserRole(userRole);
				}
			}
			
			//String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),VerificationTypeEnum.ACCOUNT.getType());
			//jdbc.update(UserQuery.INSERT_ACCOUNT_VERIFICATION_QUERY, Map.of("userId", user.getId(),"url", verificationUrl));
			//emailService.setVerificationUrl(user.getFirstName(),user.getEmail(),VerificationTypeEnum.ACCOUNT);
			
			return UserDTOMapper.fromUser(user);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ApiException("No role " + RoleEnum.USER.toString() + " found");
		}
		catch(Exception exception) {
			throw exception;// ApiException("An error occurred. Please try again.");
		}
		
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		User user =userRepository.getUserByEmail(email);
		if(user != null)
		{
			return UserDTOMapper.fromUser(user);
		}
		else{
			return null;
		}
	}

}
