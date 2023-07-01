package com.securefivewave.constaint;

public class GlobalConstaint {
	public static final String JWT_PREFIX ="Bearer ";
	public static final int PASSWORD_STRENGTH =12;
	public static final String[] UN_SECURED_URLs = {"/api/v1/auth/**"};
}
