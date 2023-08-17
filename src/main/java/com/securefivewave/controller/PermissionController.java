package com.securefivewave.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.securefivewave.dto.permission.PermissionRequest;
import com.securefivewave.dto.permission.PermissionResponse;
import com.securefivewave.entity.Permission;
import com.securefivewave.handler.response.CommonResponse;
import com.securefivewave.record.UserPermissionRecord;
import com.securefivewave.service.implementation.PermissionServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author User
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/admin/permission")
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionServiceImpl permissionService;
	
	@PostMapping("/create")
	public ResponseEntity<CommonResponse<PermissionResponse>> create (@RequestBody @Valid PermissionRequest request) throws Exception{
		
		Permission perm = new Permission();
		perm.setRoleId(request.getRoleId());
		perm.setObjectId(request.getObjId());
		perm.setCanView(request.isCanView());
		perm.setCanAdd(request.isCanAdd());
		perm.setCanUpdate(request.isCanUpdate());
		perm.setCanDelete(request.isCanDelete());
		perm.setCanAll(request.isCanAll());

		try{
			PermissionResponse res = permissionService.createPermission(request);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(res));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}

	@GetMapping("/getUserPermissionByUserIdObjectId")
	public ResponseEntity<CommonResponse<UserPermissionRecord>> getPermissionByUserIdObjectId (@RequestBody @Valid @RequestParam String email, @RequestParam Long objId) throws Exception{
		try{
			UserPermissionRecord rec = permissionService.getUserPermission(email, objId);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.successResponse(rec));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(CommonResponse.errorResponse(e.hashCode(),e.getMessage()));
		}
	}
}
