package com.securefivewave.dto.permission;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class IPermission{	 
	private Long userId;
    private Long objId;
    private boolean canView;
    private boolean canAdd;
    private boolean canUpdate;
    private boolean canDelete;
    private boolean canAll;
}