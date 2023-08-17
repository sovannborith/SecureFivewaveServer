package com.securefivewave.record;

public record UserPermissionRecord(
    Long id, 
    Long userId,
    Long objId,
    boolean canView,
    boolean canAdd,
    boolean canUpdate,
    boolean canDelete,
    boolean canAll) 
    {
        public UserPermissionRecord(Long id, Long userId,Long objId, 

            boolean canView,
            boolean canAdd,
            boolean canUpdate,
            boolean canDelete,
            boolean canAll)
            {
                this.id= id;
        this.userId=userId;
        this.objId = objId;
        this.canAdd = canAdd;
        this.canView = canView;
        this.canUpdate = canUpdate;
        this.canDelete=canDelete;
        this.canAll = canAll;
    }
}
