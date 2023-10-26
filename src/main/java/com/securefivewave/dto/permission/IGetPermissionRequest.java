package com.securefivewave.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IGetPermissionRequest {
    private String email;
    private Long objId;
}