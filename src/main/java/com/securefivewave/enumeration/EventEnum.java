package com.securefivewave.enumeration;

public enum EventEnum {

    OTP_SENT(1),
    OTP_SEND_SUCCESS(2),
    OTP_SEND_FAILED(4),
    REGISTER_ATTEMP(5),
    REGISTER_ATTEMP_SUCCESS(6),
    REGISTER_ATTEMP_FAILED(7),
    LOGIN_ATTEMP_FAILURE(8),
    LOGIN_ATTEMP_SUCCESS(9),
    LOGIN_ATTEMP(10),
    TOKEN_SENT(11),
    TOKEN_SEND_FAILED(12),
    TOKEN_SEND_SUCCESS(13),
    MFA_UPDATE(14),
    PASSWORD_UPDATE(15),
    ACCOUNT_SETTING_UPDATE(16),
    ROLE_UPDATE(17),
    PROFILE_PICTURE_UPDATE(18),
    PROFILE_UPDATE(19),
    OTP_VERIFY_SENT(20),
    OTP_VERIFY_SUCCESSED(21),
    OTP_VERIFY_FAILED(22);


    private final int type;
    EventEnum(int type){
		this.type = type;
	}
	public Long getType() {
		return Long.valueOf( this.type);
	}

}
