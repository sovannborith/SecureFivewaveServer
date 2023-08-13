package com.securefivewave.enumeration;

public enum ObjectEnum {
	USER(1),
	ROLE(2),
	USER_ROLE(3),
	OBJECT(4),
	EVENT(5),
	PERMISSION(6),
	USER_EVENT(7),
	MFA_VERIFICATION(8),
	ACCOUNT_VERIFICATION(9),
	RESET_PASSWORD_VERIFICATION(10)
	;

	private final int type;
	
	ObjectEnum(int type){
		this.type = type;
	}
	public int getType() {
		return this.type;
	}
}
