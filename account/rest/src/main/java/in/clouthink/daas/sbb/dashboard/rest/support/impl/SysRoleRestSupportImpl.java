package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.exception.RoleException;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.dashboard.rest.dto.*;
import in.clouthink.daas.sbb.dashboard.rest.support.SysRoleRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SysRoleRestSupportImpl implements SysRoleRestSupport {

	@Autowired
	private RoleService appRoleService;

	@Autowired
	private SysUserAccountService accountService;

	@Override
	public List<RoleSummary> getSysRoles(SysUser byWho) {
		return Arrays.asList(SysRole.values())
					 .stream()
					 .filter(role -> role != SysRole.ROLE_USER)
					 .map(RoleSummary::from)
					 .collect(Collectors.toList());
	}

	@Override
	public List<RoleSummary> getSysRoles4Privilege(SysUser byWho) {
		return Arrays.asList(SysRole.values())
					 .stream()
					 .filter(role -> role != SysRole.ROLE_ADMIN)
					 .map(RoleSummary::from)
					 .collect(Collectors.toList());
	}

	@Override
	public Page<SysUserSummary> getUsersBySysRoleId(String roleCode, SysUserQueryParameter request, SysUser byWho) {
		SysRole role = null;

		try {
			role = SysRole.valueOf(roleCode);
		}
		catch (RuntimeException e) {
			throw new RoleException(String.format("无效的系统角色名'%s'", roleCode));
		}
		Page<SysUser> users = accountService.listUsersByRole(role, request);
		return new PageImpl<>(users.getContent().stream().map(SysUserSummary::from).collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  users.getTotalElements());
	}

	@Override
	public void bindUsers4SysRole(String id, UsersForRoleParameter request, SysUser byWho) {
		appRoleService.bindUsers4SysRole(id, request.getUserIds());
	}

	@Override
	public void unBindUsers4SysRole(String id, UsersForRoleParameter request, SysUser byWho) {
		appRoleService.unBindUsers4SysRole(id, request.getUserIds());
	}

	@Override
	public Page<RoleSummary> getAppRoles(RoleQueryRequest request) {
		Page<SysExtRole> appRoles = appRoleService.listAppRoles(request);
		return new PageImpl<>(appRoles.getContent().stream().map(RoleSummary::from).collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  appRoles.getTotalElements());
	}

	@Override
	public List<RoleSummary> getAppRolesList() {
		return appRoleService.listAppRoles().stream().map(RoleSummary::from).collect(Collectors.toList());
	}

	@Override
	public Page<SysUserSummary> getUsersByAppRoleId(String roleId, SysUserQueryParameter request) {
		Page<SysUser> users = appRoleService.listBindUsers(roleId, request);
		return new PageImpl<>(users.getContent().stream().map(SysUserSummary::from).collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  users.getTotalElements());
	}

	@Override
	public SysExtRole createAppRole(SaveRoleParameter request) {
		return appRoleService.createAppRole(request);
	}

	@Override
	public void updateAppRole(String id, SaveRoleParameter request) {
		appRoleService.updateAppRole(id, request);
	}

	@Override
	public void deleteAppRole(String id) {
		appRoleService.deleteAppRole(id);
	}

	@Override
	public void bindUsers4AppRole(String id, UsersForRoleParameter request) {
		appRoleService.bindUsers4AppRole(id, request.getUserIds());
	}

	@Override
	public void unBindUsers4AppRole(String id, UsersForRoleParameter request) {
		appRoleService.unBindUsers4AppRole(id, request.getUserIds());
	}

}
