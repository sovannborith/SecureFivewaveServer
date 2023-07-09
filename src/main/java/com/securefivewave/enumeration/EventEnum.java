package com.securefivewave.enumeration;

public enum EventEnum {

    
    OTP_SENT(1),
    OTP_SEND_SUCCESS(2),
    OTP_SEND_FAILED(3),
    REGISTER_ATTEMP(4),
    REGISTER_ATTEMP_SUCCESS(5),
    REGISTER_ATTEMP_FAILED(6),
    LOGIN_ATTEMP_FAILURE(7),
    LOGIN_ATTEMP_SUCCESS(8),
    LOGIN_ATTEMP(9),
    TOKEN_SENT(10),
    TOKEN_SEND_FAILED(11),
    TOKEN_SEND_SUCCESS(12),
    MFA_UPDATE(13),
    PASSWORD_UPDATE(14),
    ACCOUNT_SETTING_UPDATE(15),
    ROLE_UPDATE(16),
    PROFILE_PICTURE_UPDATE(17),
    PROFILE_UPDATE(18);



    private final int type;
    EventEnum(int type){
		this.type = type;
	}
	public int getType() {
		return this.type;
	}


    ;
}
