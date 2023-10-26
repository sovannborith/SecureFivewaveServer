package com.securefivewave.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ICreatePermissionRequest {
    private Long roleId;
    private Long objId;
    private boolean canView;
    private boolean canAdd;
    private boolean canUpdate;
    private boolean canDelete;
    private boolean canAll;
}