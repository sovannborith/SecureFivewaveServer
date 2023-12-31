package com.securefivewave.service.permission;
import java.util.List;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.permission.ICreatePermissionRequest;
import com.securefivewave.dto.permission.ICreatePermissionResponse;
import com.securefivewave.dto.permission.IGetPermissionResponse;
import com.securefivewave.dto.permission.IPermission;
import com.securefivewave.entity.Permission;
import com.securefivewave.entity.User;
import com.securefivewave.repository.IPermissionRepository;
import com.securefivewave.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService{

    private final IPermissionRepository permissionRepository;
    private final UserServiceImpl userServiceImpl;

    public ICreatePermissionResponse createPermission(ICreatePermissionRequest request) {
        try{
            Permission p = getUserPermissionByRoleIdObjectId(request.getRoleId(), request.getObjId());
            if(p!=null) throw new InvalidDataAccessApiUsageException("Permission with this role and object already existed. Please use a different user or role and try again."); 
            
            Permission perm = new Permission();
            perm.setRoleId(request.getRoleId());
            perm.setObjectId(request.getObjId());
            perm.setCanView(request.isCanView());
            perm.setCanAdd(request.isCanAdd());
            perm.setCanUpdate(request.isCanUpdate());
            perm.setCanDelete(request.isCanDelete());
            perm.setCanAll(request.isCanAll());
            
            
            createPermission(perm);

            return ICreatePermissionResponse.builder()
                    .permissionRequest(request)
                    .success(true)
                    .message(GlobalConstaint.PERMISSION_ADDED)
                    .errorCode(null)
                    .build();

        }
        catch(Exception e){
            return ICreatePermissionResponse.builder()
                    .permissionRequest(request)
                    .success(false)
                    .message(GlobalConstaint.PERMISSION_ADD_FAILED)
                    .errorCode(e.hashCode())
                    .build();
        }
    }

    @Override
    public List<Permission> getUserPermissionByUserIdObjectId(Long userId, Long objId) {
        try{
            return permissionRepository.getUserPermissionByUserIdObjectId(userId, objId);
        }
        catch(Exception e){
            throw e;
        }
    }

    public IGetPermissionResponse getUserPermissionByEmailObjectId(String email, Long objId)
    {
        User user = userServiceImpl.getUserByEmail(email);

        if(user!=null){
            List<Permission> userPerm = getUserPermissionByUserIdObjectId(user.getId(),objId);
            boolean can_view =false;
            boolean can_add =false;
            boolean can_update =false;
            boolean can_delete =false;
            boolean can_all =false;

            for(Permission p : userPerm){
                if(p.getCanView()){
                    can_view =true;
                }
                if(p.getCanAdd()){
                    can_add =true;
                }
                if(p.getCanUpdate()){
                    can_update =true;
                }
                if(p.getCanDelete()){
                    can_delete =true;
                }
                if(p.getCanAll()){
                    can_all =true;
                }
            }

            IPermission perm = IPermission.builder()
                            .objId(objId)
                            .userId(user.getId())
                            .canAdd(can_add)
                            .canView(can_view)
                            .canUpdate(can_update)
                            .canDelete(can_delete)
                            .canAll(can_all)
                            .build();
            return IGetPermissionResponse.builder()
                    .permission(perm)
                    .success(true)
                    .errorCode(null)
                    .message(null)
                    .build();
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Permission> getUserPermissionByUserId(Long userId) {
        try{
            
            return permissionRepository.getUserPermissionByUserId(userId);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public void deleteUserPermissionById(Long id) {
        try{
            permissionRepository.deleteById(id);
        }
        catch(Exception e){
            throw e;
        }
        
    }

    @Override
    public Permission getUserPermissionById(Long id) {
        
        try{
            return permissionRepository.getPermissionById(id);
           
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public Permission update(Permission permission) {
        
        try{
            return permissionRepository.save(permission);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public List<Permission> getUserPermissionByObjectId(Long objId) {
        try{
            return permissionRepository.getUserPermissionByObjectId(objId);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public List<Permission> getAllUserPermission() {
        try{
            return permissionRepository.getAllUserPermission();
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public Permission getUserPermissionByRoleIdObjectId(Long roleId, Long objId) {
        try{
            return permissionRepository.getUserPermissionByRoleIdObjectId(roleId, objId);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public Permission createPermission(Permission permission) {
        try{
            return permissionRepository.save(permission);
        }
        catch(Exception e){
            throw e;
        }
    }

    
}
