package com.securefivewave.query;

public class RoleQuery {
	public static final String GET_ROLE_BY_ROLE_NAME_QUERY ="SELECT * FROM TBL_ROLE R WHERE LOWER(R.ROLE_NAME)= :roleName";
	public static final String INSERT_ROLE_QUERY = "";
}
