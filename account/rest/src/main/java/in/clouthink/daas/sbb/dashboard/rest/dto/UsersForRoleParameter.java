package in.clouthink.daas.sbb.dashboard.rest.dto;

import in.clouthink.daas.sbb.account.domain.request.UsersForRoleRequest;

import java.util.List;

public class UsersForRoleParameter implements UsersForRoleRequest {
    
    private List<String> userIds;
    
    @Override
    public List<String> getUserIds() {
        return userIds;
    }
    
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
