package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.request.UsersForRoleRequest;
import io.swagger.annotations.ApiModel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("角色和用户绑定申请")
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
