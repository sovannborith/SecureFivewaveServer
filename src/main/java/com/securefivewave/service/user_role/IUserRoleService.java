package com.securefivewave.service.user_role;

import java.util.List;

import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.entity.UserRole;

public interface IUserRoleService {
    public UserRoleDTO createUserRole(UserRole role);
    public UserRoleDTO getUserRoleByUserIdRoleId(Long userId, Long roleId);
    public UserRoleDTO getUserRoleById(Long id);
    public List<UserRoleDTO> getUserRoleByUserId(Long userId);
    public UserRoleDTO update(UserRole role);
    public boolean deleteById(Long id);
}
