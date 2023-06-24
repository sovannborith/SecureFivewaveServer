package com.securefivewave.repository;
import java.util.List;

import com.securefivewave.entity.Permission;
public interface IPermissionRepository<T extends Permission> {
	
	/*Basic CRUD Operations */
	T create(T data);
	List<T> list(int page, int pageSize);
	T get(Long id);
	T update(T data);
	Boolean delete(Long id);
	T getPermissionByRoleIdObjectId(Long roleId,Long objectId);
}
