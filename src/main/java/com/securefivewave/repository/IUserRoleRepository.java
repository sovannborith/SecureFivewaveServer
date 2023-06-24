package com.securefivewave.repository;
import java.util.List;

import com.securefivewave.entity.UserRole;

public interface IUserRoleRepository<T extends UserRole> {
	/*Basic CRUD Operations */
	T create(T data);
	List<T> list(int page, int pageSize);
	T get(Long id);
	T update(T data);
	Boolean delete(Long id);
	
	UserRole getUserRoleByUserId(Long userId);
	UserRole getUserRoleByRoleId(Long roleId);
	UserRole getUserRoleByUserIdRoleId(Long userId,Long roleId);
}
