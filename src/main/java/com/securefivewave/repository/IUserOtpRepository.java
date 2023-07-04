package com.securefivewave.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.UserOtp;

public interface IUserOtpRepository extends ListCrudRepository<UserOtp, Long> {

	@Query(value = "SELECT U.* from TBL_USER_OTP U WHERE USER_ID=:userId", nativeQuery = true)
	UserOtp getUserOtpByUserId(@Param("userId") Long userId);
	
	@Query(value = "SELECT U.* from TBL_USER_OTP U WHERE U.USER_OTP=:otp", nativeQuery=true)
	UserOtp getUserTokenByToken(@Param("otp") String otp);

}
