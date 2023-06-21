/**
 * 
 */
package com.securefivewave.enumeration;

/**
 * @author User
 *
 */
public enum VerificationTypeEnum {
	ACCOUNT("ACCOUNT"),
	PASSWORD("PASSWORD");
	
	private final String type;
	
	VerificationTypeEnum(String type){
		this.type = type;
	}
	public String getType() {
		return this.type.toLowerCase();
	}
}
