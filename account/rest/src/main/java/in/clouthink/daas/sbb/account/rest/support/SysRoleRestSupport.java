package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.rest.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRoleRestSupport {

	List<RoleSummary> getSysRoles(User byWho);

	List<RoleSummary> getSysRoles4Privilege(User byWho);

	Page<UserSummary> getUsersBySysRoleId(String roleId, UserQueryParameter request, User byWho);

	void bindUsers4SysRole(String id, UsersForRoleParameter request, User byWho);

	void unBindUsers4SysRole(String id, UsersForRoleParameter request, User byWho);

	Page<RoleSummary> getAppRoles(RoleQueryRequest request);

	List<RoleSummary> getAppRolesList();

	Page<UserSummary> getUsersByAppRoleId(String roleId, UserQueryParameter request);

	AppRole createAppRole(SaveRoleParameter request);

	void updateAppRole(String id, SaveRoleParameter request);

	void deleteAppRole(String id);

	void bindUsers4AppRole(String id, UsersForRoleParameter request);

	void unBindUsers4AppRole(String id, UsersForRoleParameter request);

}
