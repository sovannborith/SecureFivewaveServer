package com.securefivewave.query;

public class UserQuery {
	public static final String COUNT_USER_EMAIL_QUERY ="SELECT COUNT(*) FROM TBL_USER WHERE LOWER(EMAIL_ADDR)= :email";
	public static final String INSERT_USER_QUERY = "INSERT INTO TBL_USER(FIRST_NAME,LAST_NAME,EMAIL_ADDR,USER_PASSWORD) VALUES(:firstName, :lastName, :email, :password)";
	public static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM TBL_USER U WHERE LOWER(U.EMAIL_ADDR) = :email";
	public static final String INSERT_ACCOUNT_VERIFICATION_QUERY="INSERT INTO TBL_ACCOUNT_VERIFICATION(USER_ID,URL) VALUES(:userId, :url)";
}
