package com.securefivewave.service;


import com.securefivewave.entity.UserOtp;

public interface IUserOtpService {
    public UserOtp createUserToken(UserOtp userToken);
    public UserOtp getUserOtpByUserId(Long userId);
    public UserOtp getUserOtpById(Long id);
    public UserOtp getUserOtpByToken(String otp);
    public UserOtp update(UserOtp userOtp);
    public void deleteById(Long id);
}
