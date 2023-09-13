
package com.securefivewave.service.implementation;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.entity.AccountVerification;
import com.securefivewave.entity.User;
import com.securefivewave.entity.UserEvent;
import com.securefivewave.entity.UserOtp;
import com.securefivewave.enumeration.EventEnum;
import com.securefivewave.handler.response.OtpResponse;
import com.securefivewave.handler.response.VerifyOtpResponse;
import com.securefivewave.repository.IUserOtpRepository;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.IUserOtpService;
import com.securefivewave.util.email.EmailUtil;
import com.securefivewave.util.otp.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOtpServiceImpl implements IUserOtpService{
	
	private final IUserOtpRepository userOtpRepository;
	private final IUserRepository userRepository;
	private final AccountVerificationServiceImpl accountVerificationServiceImpl;
	private final OtpUtil otpUtil;
	private final EmailUtil emailUtil;
	private final UserEventServiceImpl userEventServiceImpl;

	@Override
	public UserOtp createUserToken(UserOtp userOtp) {
		return userOtpRepository.save(userOtp);
	}
	@Override
	public UserOtp getUserOtpByUserId(Long userId) {
		return userOtpRepository.getUserOtpByUserId(userId);
	}
	@Override
	public UserOtp getUserOtpById(Long id) {
		return userOtpRepository.findById(id).orElseThrow();
	}
	@Override
	public UserOtp getUserOtpByToken(String otp) {
		return userOtpRepository.getUserTokenByToken(otp);
	}
	@Override
	public UserOtp update(UserOtp userOtp) {
		return userOtpRepository.save(userOtp);
	}
	@Override
	public void deleteById(Long id) {
		userOtpRepository.deleteById(id);
	}

	public VerifyOtpResponse verifyOtp(String email, String otp)
	{
		try{
			
			User user = userRepository.getUserByEmail(email);
			
			if(user !=null)
			{
				if(user.getIsEnable()){
					generateUserEvent(user.getId(),EventEnum.OTP_VERIFY_FAILED.getType());
					return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(false)
							.message(GlobalConstaint.USER_ACCOUNT_ALREADY_VERIFIED)
							.build();
				}
				UserOtp userOtp = this.getUserOtpByUserId(user.getId());
				if(userOtp ==null){
					generateUserEvent(user.getId(),EventEnum.OTP_VERIFY_FAILED.getType());
					return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(false)
							.message(GlobalConstaint.INVALID_EMAIL_ADDRESS)
							.build();
				}
				
				if(userOtp.getUserOtp().equals(otp)){
					
					Long timeDiff =Duration.between( LocalDateTime.now(),userOtp.getOtpExpiredAt()).toMillis();
					if(timeDiff<=0){
					// Otp is already expired
					generateUserEvent(user.getId(),EventEnum.OTP_VERIFY_FAILED.getType());
					return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(false)
							.message(GlobalConstaint.OTP_IS_EXPIRED)
							.build();
					}
					//Update user to enabled
					user.setIsEnable(true);
					userRepository.save(user);

					/* UserToken userToken = userTokenServiceImpl.getUserTokenByUserId(user.getId());
					userToken.setRefreshTokenExpiryDate(new Date(System.currentTimeMillis()+ this.jwtService.getRefreshTokenExpiration()));
					userToken.setAccessTokenExpiryDate(new Date(System.currentTimeMillis() + this.jwtService.getAccessTokenExpiration()));
					userTokenServiceImpl.saveUserToken(userToken); */

					generateUserEvent(user.getId(),EventEnum.OTP_VERIFY_SUCCESSED.getType());
					return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(true)
							.message(GlobalConstaint.USER_ACCOUNT_IS_VERIFIED)
							.build();
				}
				else{
					generateUserEvent(user.getId(),EventEnum.OTP_VERIFY_FAILED.getType());
					return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(false)
							.message(GlobalConstaint.OTP_IS_INVALID)
							.build();
				}
			}
			else{
				
				return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, otp))
							.success(false)
				.message(GlobalConstaint.INVALID_EMAIL_ADDRESS)
				.build();
			}
		}
		catch (Exception e){
			throw e;
		}
	}

	public VerifyOtpResponse regenerateOtp(String email){
		try{
			User user = userRepository.getUserByEmail(email);
			if(user==null) return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, null))
							.success(false)
				.message(GlobalConstaint.INVALID_EMAIL_ADDRESS)
				.build();

			UserOtp userOtp = userOtpRepository.getUserOtpByUserId(user.getId());
			if(userOtp ==null) 
			{
				return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, null))
							.success(false)
				.message(GlobalConstaint.INVALID_EMAIL_ADDRESS)
				.build();
			}
			String newOtp = otpUtil.generateOtp();
			userOtp.setUserOtp(newOtp);
			userOtp.setOtpExpiredAt(LocalDateTime.now().plusMinutes(GlobalConstaint.OTP_EXPIRED_MINUTE));
			
			userOtpRepository.save(userOtp);
			UUID uuid = UUID.randomUUID();
			String url = GlobalConstaint.CLIENT_BASED_URL + "/verifyOtp?email=" + user.getEmail() + "&otp=" + userOtp.getUserOtp() + "&uid=" + uuid.toString();
			AccountVerification av =AccountVerification.builder()
							.userId(user.getId())
							.url(url)
							.createdAt(LocalDateTime.now())
							.build();
			accountVerificationServiceImpl.createAccountVerfication(av);
			sendAccountVerificationEmail(user,url);

				return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, newOtp))
							.success(true)
				.message(GlobalConstaint.OTP_RESEND_SUCCESSFUL)
				.build();

		}
		catch(Exception e){
			return VerifyOtpResponse.builder()
							.otpResponse(new OtpResponse(email, null))
							.success(false)
				.message(GlobalConstaint.OTP_COMMON_ERROR + " <br><br>" + e.getMessage())
				.build();
		}
		
	}
	
	public void sendAccountVerificationEmail(User user, String url) throws UnsupportedEncodingException, MessagingException
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
	
	private void generateUserEvent(Long userId, Long eventId){

		UserEvent userEvent = new UserEvent();
			userEvent.setUserId(userId);
			userEvent.setEventId(eventId);
			userEvent.setDevice(null);
			userEvent.setIpAddress(null);
			userEvent.setCreatedAt(LocalDateTime.now());
		userEventServiceImpl.createUserEvent(userEvent);
	}
}
