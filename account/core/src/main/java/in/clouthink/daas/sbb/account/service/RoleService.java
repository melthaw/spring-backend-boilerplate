package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveRoleRequest;
import in.clouthink.daas.sbb.account.domain.request.UserQueryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    
    Page<ExtRole> listAppRoles(RoleQueryRequest request);

    List<ExtRole> listAppRoles();

    Page<User> listBindUsers(String id, UserQueryRequest request);
    
    ExtRole findById(String id);

    ExtRole findByCode(String code);

    ExtRole createAppRole(SaveRoleRequest request);
    
    void updateAppRole(String id, SaveRoleRequest request);
    
    void deleteAppRole(String id);
    
    void bindUsers4AppRole(String id, List<String> userIds);
    
    void unBindUsers4AppRole(String id, List<String> userIds);
    
    void bindUsers4SysRole(String id, List<String> userIds);
    
    void unBindUsers4SysRole(String id, List<String> userIds);
    
}
