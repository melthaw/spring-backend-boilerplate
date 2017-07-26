package in.clouthink.daas.sbb.rbac.impl.service;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveRoleRequest;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    
    Page<SysExtRole> listAppRoles(RoleQueryRequest request);

    List<SysExtRole> listAppRoles();

    Page<SysUser> listBindUsers(String id, SysUserQueryRequest request);
    
    SysExtRole findById(String id);

    SysExtRole findByCode(String code);

    SysExtRole createAppRole(SaveRoleRequest request);
    
    void updateAppRole(String id, SaveRoleRequest request);
    
    void deleteAppRole(String id);
    
    void bindUsers4AppRole(String id, List<String> userIds);
    
    void unBindUsers4AppRole(String id, List<String> userIds);
    
    void bindUsers4SysRole(String id, List<String> userIds);
    
    void unBindUsers4SysRole(String id, List<String> userIds);
    
}
