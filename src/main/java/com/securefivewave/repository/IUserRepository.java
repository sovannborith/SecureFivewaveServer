package com.securefivewave.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.User;


public interface IUserRepository extends ListCrudRepository<User, Long> {
	
	@Query(value = "SELECT U.* from TBL_USER U WHERE LOWER(U.email_addr)=LOWER(:email)", nativeQuery = true)
	User getUserByEmail(@Param("email") String email);

	@Query(value = "UPDATE TBL_USER SET IS_ENABLE=1 WHERE ID=:userId", nativeQuery = true)
	boolean enableUser(@Param("userId") Long userId);
}
