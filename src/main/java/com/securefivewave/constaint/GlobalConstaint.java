package com.securefivewave.constaint;

public class GlobalConstaint {

	public static final String JWT_PREFIX ="Bearer ";

	public static final int PASSWORD_STRENGTH =12;

    public static final int OTP_EXPIRED_MINUTE =5;

	public static final String AUTH_HEADER ="Authorization";

	public static final String[] UN_SECURED_URLs = {"/api/v1/auth/**"};

    public static final String BASED_URL ="http://localhost:9191/api/v1";
	
	public static final String INVALID_USER = "Invalid user";

    public static final String USER_NOT_FOUND = "User not found";

    public static final String USER_IS_NOT_FULLY_REGISTER = "User is not fully registered";

    public static final String USER_IS_NOT_REGISTERED = "User is already registered";

    public static final String FORBIDDEN_ACCESS = "403 access denied";

    public static final String INVALID_BEARER_TOKEN = "Invalid bearer token";

    public static final String TOKEN_IS_EXPIRED = "Token is already expired";

    public static final String OTP_IS_INVALID = "Otp is invalid";
    public static final String OTP_IS_EXPIRED ="Otp is expired";

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

}
