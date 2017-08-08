package in.clouthink.daas.sbb.account.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.rest.support.ExtRoleRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ExtRoleRestSupport mocker
 * @author dz
 */
@Component
public class ExtRoleRestSupportMockImpl implements ExtRoleRestSupport {
	@Override
	public List<RoleSummary> getSysRoles() {
		return null;
	}

	@Override
	public List<RoleSummary> getSysRoles4Privilege() {
		return null;
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
	public Page<UserSummary> getUsersBySysRoleId(String roleId, UserQueryParameter request) {
		return null;
	}

	@Override
	public Page<UserSummary> getUsersByAppRoleId(String roleId, UserQueryParameter request) {
		return null;
	}

	@Override
	public ExtRole createAppRole(SaveRoleParameter request) {
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

	@Override
	public void bindUsers4SysRole(String id, UsersForRoleParameter request) {

	}

	@Override
	public void unBindUsers4SysRole(String id, UsersForRoleParameter request) {

	}
}
