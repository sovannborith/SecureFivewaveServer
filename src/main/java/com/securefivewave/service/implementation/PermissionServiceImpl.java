package com.securefivewave.service.implementation;
import java.util.List;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.securefivewave.constaint.GlobalConstaint;
import com.securefivewave.dto.permission.PermissionRequest;
import com.securefivewave.dto.permission.PermissionResponse;
import com.securefivewave.entity.Permission;
import com.securefivewave.record.UserPermissionRecord;
import com.securefivewave.repository.IPermissionRepository;
import com.securefivewave.service.IPermissionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService{

    private final IPermissionRepository permissionRepository;

    public PermissionResponse createPermission(PermissionRequest request) {
        try{
            Permission p = getPermissionByRoleIdObjectId(request.getRoleId(), request.getObjId());
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

            return PermissionResponse.builder()
                    .permissionRequest(request)
                    .success(true)
                    .message(GlobalConstaint.PERMISSION_ADDED)
                    .errorCode(null)
                    .build();

        }
        catch(Exception e){
            return PermissionResponse.builder()
                    .permissionRequest(request)
                    .success(false)
                    .message(GlobalConstaint.PERMISSION_ADD_FAILED)
                    .errorCode(e.hashCode())
                    .build();
        }
    }

    public UserPermissionRecord getUserPermission(Long userId)
    {
        List<Permission> userPerm = getUserPermissionByUserId(userId);
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
            
            return new UserPermissionRecord(userId,can_view,can_add,can_update,can_delete,can_all);
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
    public void deleteById(Long id) {
        try{
            permissionRepository.deleteById(id);
        }
        catch(Exception e){
            throw e;
        }
        
    }

    @Override
    public Permission getPermissionById(Long id) {
        
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
    public Permission getPermissionByRoleIdObjectId(Long roleId, Long objId) {
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
