
package com.securefivewave.service.user;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.UserDTO;
import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.dto.dtomapper.UserDTOMapper;
import com.securefivewave.dto.user.ChangePasswordResponse;
import com.securefivewave.entity.AccountVerification;
import com.securefivewave.entity.Role;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserEvent;
import com.securefivewave.entity.UserOtp;
import com.securefivewave.entity.UserRole;
import com.securefivewave.entity.UserToken;
import com.securefivewave.enumeration.EventEnum;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.enumeration.TokenTypeEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.account_verification.AccountVerificationServiceImpl;
import com.securefivewave.service.user_event.UserEventServiceImpl;
import com.securefivewave.service.jwt.JwtService;
import com.securefivewave.service.user_otp.UserOtpServiceImpl;
import com.securefivewave.service.user_role.UserRoleServiceImpl;
import com.securefivewave.service.user_token.UserTokenServiceImpl;
import com.securefivewave.util.otp.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService{
	
	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final UserRoleServiceImpl userRoleServiceImpl;
	private final OtpUtil otpUtil;
	private final UserOtpServiceImpl userOtpServiceImpl;
	private final AccountVerificationServiceImpl accountVerificationServiceImpl;
	private final JwtService jwtService;
	private final UserTokenServiceImpl userTokenServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final UserEventServiceImpl userEventServiceImpl;

	@Override
	public User createUser(User user) throws Exception {
		
		if(getUserByEmail(user.getEmail().trim().toLowerCase())!=null) throw new ApiException("Email already in use. Please use a different email and try again.");
		try
		{
			user.setIsEnable(false);
			user.setIsLocked(false);
			userRepository.save(user);

			generateUserEvent(user.getId(), EventEnum.REGISTER_ATTEMP_SUCCESS.getType());
			
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
			
			UserOtp userOtp = UserOtp.builder()
							.userId(user.getId())
							.userOtp(otpUtil.generateOtp())
							.createdAt(LocalDateTime.now())
							.otpExpiredAt(LocalDateTime.now().plusMinutes(GlobalConstaint.OTP_EXPIRED_MINUTE))
							.build();
			// Save user token
			//SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user, userRoleServiceImpl, roleServiceImpl);
			String accessToken = jwtService.generateToken(user.getEmail());
			revokedAllValidUserTokenByUserId(user.getId());// Revoked all valid tokens

			UserToken userToken = UserToken.builder()
						.userId(user.getId())
						.accessToken(accessToken)
						.refreshToken(accessToken)
						.tokenType(TokenTypeEnum.BEARER.toString())
						.accessTokenExpiryDate(new Date(System.currentTimeMillis() + this.jwtService.getAccessTokenExpiration()))
						.refreshTokenExpiryDate(new Date(System.currentTimeMillis() + this.jwtService.getRefreshTokenExpiration()))
						.build();

			saveUserToken(userToken); // Save user token			


			// Save user Otp
			userOtpServiceImpl.createUserToken(userOtp);
			// Save Account verification
			UUID uuid = UUID.randomUUID();
			String url = GlobalConstaint.CLIENT_BASED_URL + "/verifyOtp?email=" + user.getEmail() + "&otp=" + userOtp.getUserOtp() + "&uid=" + uuid.toString();
			AccountVerification av =AccountVerification.builder()
							.userId(user.getId())
							.url(url)
							.createdAt(LocalDateTime.now())
							.build();
			accountVerificationServiceImpl.createAccountVerfication(av);
			// Send email verfication
			sendAccountVerificationEmail(user,url);
			//String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),VerificationTypeEnum.ACCOUNT.getType());
			
			return user;
		}
		catch(EmptyResultDataAccessException exception) {
			generateUserEvent(user.getId(), EventEnum.REGISTER_ATTEMP_FAILED.getType());
			throw new ApiException("No role " + RoleEnum.USER.toString() + " found");
		}
		catch(Exception exception) {
			generateUserEvent(user.getId(), EventEnum.REGISTER_ATTEMP_FAILED.getType());
			throw exception;// ApiException("An error occurred. Please try again.");
		}
		
	}

	@Override
	public User getUserByEmail(String email) {
		User user =userRepository.getUserByEmail(email);
		if(user != null)
		{
			return user;
		}
		else{
			return null;
		}
	}

	@Override
	public List<UserDTO> getAllUsers(){

		List<UserDTO> users = new ArrayList<>();

		for (User user : userRepository.findAll()) {
			users.add(UserDTOMapper.fromUser(user));
		}
		return users;
	}

	@Override
	public User update(User user){
		return userRepository.save(user);
	}
	@Override
	public boolean enableUser(Long userId){
		userRepository.enableUser(userId);
		return true;
	}

	public boolean login (String email, String password)
	{
		try{			
			
			Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
			User user = userRepository.getUserByEmail(email);
			if(authentication.isAuthenticated())
			{
				generateUserEvent(user.getId(), EventEnum.LOGIN_ATTEMP_SUCCESS.getType());
				return true;
			}
			else
			{
				generateUserEvent(user.getId(), EventEnum.LOGIN_ATTEMP_FAILURE.getType());
				return false;
			}
			
		}
		catch(Exception e)
		{
			throw new UsernameNotFoundException("Operation Failed! Please try again.");
		}
	}

	private void sendAccountVerificationEmail(User user, String url) throws UnsupportedEncodingException, MessagingException
	{
		try{
			userOtpServiceImpl.sendAccountVerificationEmail(user, url);
			generateUserEvent(user.getId(), EventEnum.OTP_VERIFY_SENT.getType());
		}
		catch(Exception e)
		{
			generateUserEvent(user.getId(), EventEnum.OTP_SEND_FAILED.getType());
			throw e;
		}

		
	}

	private void saveUserToken(UserToken userToken)
	{						
		try
		{			
			userTokenServiceImpl.saveUserToken(userToken);
		}			
		catch(Exception e)
		{
			throw e;
		}			
	}

	private void revokedAllValidUserTokenByUserId(Long Id)
	{						
		userTokenServiceImpl.revokedAllValidUserTokenByUserId(Id);				
	}

	private void generateUserEvent(Long userId, Long eventId){

		UserEvent userEvent = new UserEvent();
			userEvent.setUserId(userId);
			userEvent.setEventId(eventId);
			userEvent.setDevice(null);
			userEvent.setIpAddress(null);
			userEvent.setCreatedAt(LocalDateTime.now());
		userEventServiceImpl.createUserEvent(userEvent);
	}

	public ChangePasswordResponse changeUserPassword(String email, String oldPwd, String newPwd){
		try{
			boolean isLogin = login(email, oldPwd);
			
			String newPassword = passwordEncoder.encode(newPwd);
			if(isLogin){
				User user =  userRepository.getUserByEmail(email);
				user.setPassword(newPassword);
				update(user);
				return new ChangePasswordResponse(email, newPassword);
			}
			else{
				throw new UsernameNotFoundException("Change password failed! Please try again.");
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}

}
