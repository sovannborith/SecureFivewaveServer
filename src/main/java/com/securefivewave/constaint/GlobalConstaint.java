package com.securefivewave.constaint;

public class GlobalConstaint {

	public static final String JWT_PREFIX ="Bearer ";

	public static final int PASSWORD_STRENGTH =12;

    public static final int OTP_EXPIRED_MINUTE =5;

	public static final String AUTH_HEADER ="Authorization";

	public static final String[] UN_SECURED_URLs = {"/","home","/api/v1/auth/**"};

    public static final String[] SECURED_URLs = {"notification","setting/**","/api/v1/admin/**"};

    public static final String SERVER_BASED_URL ="http://localhost:9191/api/v1";

    public static final String CLIENT_BASED_URL ="http://localhost:4200";
	
	public static final String INVALID_USER = "Invalid user";

    public static final String USER_NOT_FOUND = "User not found";

    public static final String USER_IS_NOT_FULLY_REGISTER = "User is not fully registered";

    public static final String USER_ACCOUNT_ALREADY_VERIFIED = "User account already verified. Please proceed to login.";

    public static final String USER_ACCOUNT_IS_VERIFIED = "User account is verified successfully";

    public static final String USER_IS_NOT_REGISTERED = "User is already registered";

    public static final String FORBIDDEN_ACCESS = "403 access denied";

    public static final String INVALID_BEARER_TOKEN = "Invalid bearer token";

    public static final String TOKEN_IS_EXPIRED = "Token is already expired";

    public static final String OTP_IS_INVALID = "Otp is invalid";

    public static final String REGISTER_SUCCESS = "Registration is success. Please check your email to verify our account.";

    public static final String REGISTER_FAILED = "Registration failed. Please try again.";

    public static final String OTP_COMMON_ERROR = "Error in sending new Otp. Please try again.";

    public static final String OTP_RESEND_SUCCESSFUL = "New Otp successfully resend. Please check your email and verify.";

    public static final String OTP_IS_EXPIRED ="Otp is expired. Please generate a new one.";

    public static final String REFRESH_TOKEN_CANNOT_GENERATE = "Refresh Token Can't Generated";

    public static final String TOKEN_CANNOT_GENERATE = "Token Can't Generated";

    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";

    public static final String INVALID_EMAIL_ADDRESS = "Invalid email address";

    public static final String INVALID_EMAIL_REGISTER_VIOLATION = "Invalid email Register Violation";

    public static final String INVALID_EMAIL_LOGIN_VIOLATION = "Invalid email Login Violation";

    public static final String INVALID_IP = "Invalid ip address";

    public static final String SOMETIME_WENT_WRONG = "something wrong with the request";

    public static final String INVALID_FILE_EXTENSION = "Invalid file extension";

    public static final String INVALID_FILE_FORMAT = "Invalid file format";

    public static final String INVALID_FILE_SIZE = "Invalid file size";

    public static final String LOGIN_FAILED = "Login failed. Please try again.";

    public static final String LOGIN_SUCCESS = "Login success.";

    public static final String PERMISSION_ADDED ="Permission added.";

    public static final String PERMISSION_ADD_FAILED ="Adding permission failed. Please try again.";

    public static final String PERMISSION_UPDATED ="Permission updated.";

    public static final String PERMISSION_UPDATE_FAILED ="Failed updating permission. Please try again.";

    public static final String PERMISSION_DELETED ="Permission deleted.";

    public static final String PERMISSION_DELETE_FAILED ="Failed deleting permission. Please try again.";

}
