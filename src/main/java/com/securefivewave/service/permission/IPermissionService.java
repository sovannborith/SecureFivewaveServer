package com.securefivewave.service.permission;

import java.util.List;

import com.securefivewave.entity.Permission;

public interface IPermissionService {
    public Permission createPermission(Permission permission);
    public List<Permission> getUserPermissionByUserId(Long userId);
    public List<Permission> getUserPermissionByObjectId(Long objId);
    public List<Permission> getUserPermissionByUserIdObjectId(Long userId, Long objId);
    public List<Permission> getAllUserPermission();
    public Permission getUserPermissionById(Long id);
    public Permission getUserPermissionByRoleIdObjectId(Long roleId, Long objId);
    public Permission update(Permission permission);
    public void deleteUserPermissionById(Long id);
}
