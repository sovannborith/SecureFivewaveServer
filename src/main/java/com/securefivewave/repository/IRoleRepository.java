package com.securefivewave.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.Role;
public interface IRoleRepository extends ListCrudRepository<Role, Long> {
		
	@Query(value = "SELECT R.* FROM TBL_ROLE R WHERE LOWER(ROLE_NAME)=LOWER(:roleName)",nativeQuery=true)
	Role getRoleByRoleName(@Param("roleName") String roleName);
}
