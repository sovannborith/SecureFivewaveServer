
package com.securefivewave.service.implementation;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.securefivewave.auth.service.SecureFivewaveUserDetail;
import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.UserDTO;
import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.dto.dtomapper.UserDTOMapper;
import com.securefivewave.entity.AccountVerification;
import com.securefivewave.entity.Role;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserOtp;
import com.securefivewave.entity.UserRole;
import com.securefivewave.enumeration.RoleEnum;
import com.securefivewave.exception.ApiException;
import com.securefivewave.jwt.JwtService;
import com.securefivewave.repository.IRoleRepository;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.IUserService;
import com.securefivewave.util.email.EmailUtil;
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
	private final RoleServiceImpl roleServiceImpl;
	private final EmailUtil emailUtil;

	@Override
	public UserDTO createUser(User user) throws Exception {
		log.info("Adding new user...");
		
		if(getUserByEmail(user.getEmail().trim().toLowerCase())!=null) throw new ApiException("Email already in use. Please use a different email and try again.");
		try
		{
			//user.setIsEnable(true);
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
			
			UserOtp userOtp = UserOtp.builder()
							.userId(user.getId())
							.userOtp(otpUtil.generateOtp())
							.createdAt(LocalDateTime.now())
							.otpExpiredAt(LocalDateTime.now().plusMinutes(GlobalConstaint.OTP_EXPIRED_MINUTE))
							.build();
			// Save user token
			SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user, userRoleServiceImpl, roleServiceImpl);
			String jwtToken = jwtService.generateToken(userDetails);
			revokedAllValidUserTokenByUserId(user.getId());// Revoked all valid tokens
			saveUserToken(user,jwtToken); // Save user token
			
			// Save user Otp
			userOtpServiceImpl.createUserToken(userOtp);
			// Save Account verification
			UUID uuid = UUID.randomUUID();
			String url = GlobalConstaint.BASED_URL + "/auth/verify-otp?email=" + user.getEmail() + "&otp=" + userOtp.getUserOtp() + "&uid=" + uuid.toString();
			AccountVerification av =AccountVerification.builder()
							.userId(user.getId())
							.url(url)
							.createdAt(LocalDateTime.now())
							.build();
			accountVerificationServiceImpl.createAccountVerfication(av);
			// Send email verfication
			sendAccountVerificationEmail(user,url);
			//String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),VerificationTypeEnum.ACCOUNT.getType());
			
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
		return userRepository.enableUser(userId);
	}

	private void sendAccountVerificationEmail(User user, String url) throws UnsupportedEncodingException, MessagingException
	{
		String sendTo = user.getEmail();
		String emailSubject = "OTP Verfication";
		String emailBody ="<p>Hi, " + user.getFirstName() + "</p>";
				emailBody +="<p>Thank you for registering with us, ";
				emailBody +="Please, follow the link below to complete your registration.</p>";
				emailBody +="<a href=\"" + url + "\">Verify your OTP to activate your account</a>";
				emailBody +="<p>Thank you, <br> Users Registration Portal Service</p>";
		emailUtil.sendOtpEmail(sendTo, emailSubject,emailBody);
	}

	private void saveUserToken(User user, String jwtToken)
	{						
		userTokenServiceImpl.saveUserToken(user,jwtToken);						
	}

	private void revokedAllValidUserTokenByUserId(Long Id)
	{						
		userTokenServiceImpl.revokedAllValidUserTokenByUserId(Id);				
	}

}
