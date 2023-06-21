package com.securefivewave.query;

public class UserRoleQuery {
	public static final String INSERT_USER_ROLE_QUERY ="INSERT INTO TBL_USER_ROLE(USER_ID,ROLE_ID) VALUES(:userId, :roleId)";
	public static final String GET_USER_ROLE_BY_USERID_ROLEID_QUERY ="SELECT * FROM TBL_USER_ROLE WHERE USER_ID=:userId AND ROLE_ID=:roleId";
}
