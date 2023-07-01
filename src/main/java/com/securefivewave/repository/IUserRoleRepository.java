package com.securefivewave.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import com.securefivewave.entity.UserRole;

public interface IUserRoleRepository extends ListCrudRepository<UserRole, Long> {

	@Query(value = "SELECT U.* from TBL_USER_ROLE U WHERE USER_ID=:userId", nativeQuery = true)
	List<UserRole> getUserRoleByUserId(@Param("userId") Long userId);
	@Query(value = "SELECT U.* from TBL_USER_ROLE U WHERE ROLE_ID=:roleId", nativeQuery=true)
	List<UserRole> getUserRoleByRoleId(@Param("roleId") Long roleId);
	@Query(value = "SELECT U.* from TBL_USER_ROLE U WHERE USER_ID=:userId AND ROLE_ID=:roleId", nativeQuery=true)
	UserRole getUserRoleByUserIdRoleId(@Param("userId") Long userId,@Param("roleId") Long roleId);
}
