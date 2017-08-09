package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.rest.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppRoleRestSupport {

	List<RoleSummary> getSysRoles();

	List<RoleSummary> getSysRoles4Privilege();

	Page<RoleSummary> getAppRoles(RoleQueryRequest request);

	List<RoleSummary> getAppRolesList();

	Page<UserSummary> getUsersBySysRoleId(String roleId, UserQueryParameter request);

	Page<UserSummary> getUsersByAppRoleId(String roleId, UserQueryParameter request);

	AppRole createAppRole(SaveRoleParameter request);

	void updateAppRole(String id, SaveRoleParameter request);

	void deleteAppRole(String id);

	void bindUsers4AppRole(String id, UsersForRoleParameter request);

	void unBindUsers4AppRole(String id, UsersForRoleParameter request);

	void bindUsers4SysRole(String id, UsersForRoleParameter request);

	void unBindUsers4SysRole(String id, UsersForRoleParameter request);

}
