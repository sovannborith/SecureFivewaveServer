package com.securefivewave.query;

public class UserQuery {
	public static final String COUNT_USER_EMAIL_QUERY ="SELECT COUNT(*) FROM TBL_USERS WHERE LOWER(EMAIL_ADDR)= :email";
	public static final String INSERT_USER_QUERY = "INSERT INTO TBL_USERD(FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) VALUES(:firstName, :lastName, :email, :password)";
	public static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM TBL_USERS U WHERE LOWER(U.EMAIL_ADDR) = :email";
	public static final String INSERT_ACCOUNT_VERIFICATION_QUERY="INSERT INTO TBL_ACCOUNT_VERIFICATION(USER_ID,URL) VALUES(:userId, :url)";
}
