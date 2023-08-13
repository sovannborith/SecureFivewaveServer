package com.securefivewave.record;

public record UserPermissionRecord( Long userId,
    boolean canView,
    boolean canAdd,
    boolean canUpdate,
    boolean canDelete,
    boolean canAll) 
    {
        public UserPermissionRecord(Long userId, 
            boolean canView,
            boolean canAdd,
            boolean canUpdate,
            boolean canDelete,
            boolean canAll)
            {
        this.userId=userId;
        this.canAdd = canAdd;
        this.canView = canView;
        this.canUpdate = canUpdate;
        this.canDelete=canDelete;
        this.canAll = canAll;
    }
}
