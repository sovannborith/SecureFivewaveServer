package com.securefivewave.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.UserToken;

public interface IUserTokenRepository extends ListCrudRepository<UserToken, Long> {

	@Query(value = "SELECT U.* from TBL_USER_TOKEN U WHERE USER_ID=:userId", nativeQuery = true)
	List<UserToken> getUserTokenByUserId(@Param("userId") Long userId);
	
	@Query(value = "SELECT U.* from TBL_USER_TOKEN U WHERE U.ACCESS_TOKEN=:token", nativeQuery=true)
	UserToken getUserTokenByToken(@Param("token") String token);

	@Query(value = "SELECT U.* from TBL_USER_TOKEN U WHERE U.USER_ID=:userId AND IS_EXPIRED=0 AND IS_REVOKED=0", nativeQuery=true)
	List<UserToken> getAllValidUserTokenByUserId(@Param("userId") Long userId);
	/* @Query(value = "SELECT U.* from TBL_USER_ U WHERE USER_ID=:userId AND ROLE_ID=:roleId", nativeQuery=true)
	UserToken getUserTokenById(@Param("id") Long id); */
}
