package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.rest.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRoleRestSupport {

	List<RoleSummary> getSysRoles(SysUser byWho);

	List<RoleSummary> getSysRoles4Privilege(SysUser byWho);

	Page<SysUserSummary> getUsersBySysRoleId(String roleId, SysUserQueryParameter request, SysUser byWho);

	void bindUsers4SysRole(String id, UsersForRoleParameter request, SysUser byWho);

	void unBindUsers4SysRole(String id, UsersForRoleParameter request, SysUser byWho);

	Page<RoleSummary> getAppRoles(RoleQueryRequest request);

	List<RoleSummary> getAppRolesList();

	Page<SysUserSummary> getUsersByAppRoleId(String roleId, SysUserQueryParameter request);

	SysExtRole createAppRole(SaveRoleParameter request);

	void updateAppRole(String id, SaveRoleParameter request);

	void deleteAppRole(String id);

	void bindUsers4AppRole(String id, UsersForRoleParameter request);

	void unBindUsers4AppRole(String id, UsersForRoleParameter request);

}
