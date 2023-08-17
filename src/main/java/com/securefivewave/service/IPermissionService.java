package com.securefivewave.service;

import java.util.List;

import com.securefivewave.entity.Permission;

public interface IPermissionService {
    public Permission createPermission(Permission permission);
    public List<Permission> getUserPermissionByUserId(Long userId);
    public List<Permission> getUserPermissionByObjectId(Long objId);
    public List<Permission> getUserPermissionByUserIdObjectId(Long userId, Long objId);
    public List<Permission> getAllUserPermission();
    public Permission getPermissionById(Long id);
    public Permission getPermissionByRoleIdObjectId(Long roleId, Long objId);
    public Permission update(Permission permission);
    public void deleteById(Long id);
}
