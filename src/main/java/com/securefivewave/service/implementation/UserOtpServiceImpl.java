
package com.securefivewave.service.implementation;

import org.springframework.stereotype.Service;
import com.securefivewave.entity.UserOtp;
import com.securefivewave.repository.IUserOtpRepository;
import com.securefivewave.service.IUserOtpService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOtpServiceImpl implements IUserOtpService{
	
	private final IUserOtpRepository userOtpRepository;
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
	
}
