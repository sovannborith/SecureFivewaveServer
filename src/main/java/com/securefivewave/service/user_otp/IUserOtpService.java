package com.securefivewave.service.user_otp;


import com.securefivewave.entity.UserOtp;
import com.securefivewave.handler.response.VerifyOtpResponse;

public interface IUserOtpService {
    public UserOtp createUserToken(UserOtp userToken);
    public UserOtp getUserOtpByUserId(Long userId);
    public UserOtp getUserOtpById(Long id);
    public UserOtp getUserOtpByToken(String otp);
    public UserOtp update(UserOtp userOtp);
    public void deleteById(Long id);
    public VerifyOtpResponse regenerateOtp(String email);
}
