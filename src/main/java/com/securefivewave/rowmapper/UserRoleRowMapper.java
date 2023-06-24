/**
 * 
 */
package com.securefivewave.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.securefivewave.entity.UserRole;

/**
 * @author User
 *
 */

public class UserRoleRowMapper implements RowMapper<UserRole>{

	@Override
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		return UserRole.builder()
				.id(rs.getLong("id"))
				.userId(rs.getLong("user_id"))
				.roleId(rs.getLong("role_id"))
				.build();
	}
	
}
