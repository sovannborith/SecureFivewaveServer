package com.securefivewave.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.Permission;
public interface IPermissionRepository extends ListCrudRepository<Permission, Long> {

	@Query(value = "SELECT P.* FROM TBL_USER_ROLE UR INNER JOIN TBL_PERMISSION P ON UR.role_id = P.role_id AND UR.user_id=:userId", nativeQuery=true)
	List<Permission> getUserPermissionByUserId(@Param("userId") Long userId);

	@Query(value = "SELECT P.* FROM TBL_USER_ROLE UR INNER JOIN TBL_PERMISSION P ON UR.role_id = P.role_id AND UR.user_id=:userId and p.obj_id=:objId", nativeQuery=true)
	List<Permission> getUserPermissionByUserIdObjectId(@Param("userId") Long userId, @Param("objId") Long objId);

	@Query(value = "SELECT U.* from TBL_PERMISSION U WHERE OBJ_ID=:objId", nativeQuery=true)
	List<Permission> getUserPermissionByObjectId(@Param("objId") Long objId);

	@Query(value = "SELECT U.* from TBL_PERMISSION ORDER BY U.ROLE_ID", nativeQuery=true)
	List<Permission> getAllUserPermission();

	@Query(value = "SELECT U.* from TBL_PERMISSION U WHERE U.ID=@id", nativeQuery=true)
	Permission getPermissionById(@Param("id") Long id);

	@Query(value = "SELECT U.* from TBL_PERMISSION U WHERE ROLE_ID=:roleId AND OBJ_ID=:objId", nativeQuery=true)
	Permission getUserPermissionByRoleIdObjectId(@Param("roleId") Long userId,@Param("objId") Long objId);
}
