package in.clouthink.daas.sbb.account.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.rest.support.SysRoleRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SysRoleRestSupport mocker
 *
 * @author dz
 */
@Component
public class SysRoleRestSupportMockImpl implements SysRoleRestSupport {
	@Override
	public List<RoleSummary> getSysRoles(User byWho) {
		return null;
	}

	@Override
	public List<RoleSummary> getSysRoles4Privilege(User byWho) {
		return null;
	}

	@Override
	public Page<UserSummary> getUsersBySysRoleId(String roleId, UserQueryParameter request, User byWho) {
		return null;
	}

	@Override
	public void bindUsers4SysRole(String id, UsersForRoleParameter request, User byWho) {

	}

	@Override
	public void unBindUsers4SysRole(String id, UsersForRoleParameter request, User byWho) {

	}

	@Override
	public Page<RoleSummary> getAppRoles(RoleQueryRequest request) {
		return null;
	}

	@Override
	public List<RoleSummary> getAppRolesList() {
		return null;
	}

	@Override
	public Page<UserSummary> getUsersByAppRoleId(String roleId, UserQueryParameter request) {
		return null;
	}

	@Override
	public AppRole createAppRole(SaveRoleParameter request) {
		return null;
	}

	@Override
	public void updateAppRole(String id, SaveRoleParameter request) {

	}

	@Override
	public void deleteAppRole(String id) {

	}

	@Override
	public void bindUsers4AppRole(String id, UsersForRoleParameter request) {

	}

	@Override
	public void unBindUsers4AppRole(String id, UsersForRoleParameter request) {

	}
}
