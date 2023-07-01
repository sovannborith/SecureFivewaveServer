package com.securefivewave.service;


import com.securefivewave.dto.RoleDTO;
import com.securefivewave.entity.Role;

public interface IRoleService {
    public RoleDTO createRole(Role role);
    public RoleDTO getRoleByRoleName(String roleName);
    public RoleDTO getRoleById(Long id);
    public RoleDTO update(Role role);
    public boolean deleteById(Long id);
}
