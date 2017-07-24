package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.dashboard.rest.dto.RoleSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserQueryParameter;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.UsersForRoleParameter;
import in.clouthink.daas.sbb.dashboard.rest.support.SysRoleRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("角色管理")
@RestController
@RequestMapping("/api/roles")
public class SysRoleRestControler {

	@Autowired
	private SysRoleRestSupport sysRoleRestSupport;

	@ApiOperation(value = "获取内置角色（角色管理）")
	@RequestMapping(value = "/sysroles", method = RequestMethod.GET)
	public List<RoleSummary> getSysRoles() {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return sysRoleRestSupport.getSysRoles(user);
	}

	@ApiOperation(value = "获取内置角色(权限管理)")
	@RequestMapping(value = "/sysroles/privilege", method = RequestMethod.GET)
	public List<RoleSummary> getSysRoles4Privilege() {
		SysUser user = (SysUser)SecurityContexts.getContext().requireUser();
		return sysRoleRestSupport.getSysRoles4Privilege(user);
	}

	@ApiOperation(value = "获取内置角色对应用户")
	@RequestMapping(value = "/sysroles/{id}/users", method = RequestMethod.GET)
	public Page<SysUserSummary> getUsersBySysRoleId(@PathVariable String id, SysUserQueryParameter request) {
		SysUser user = (SysUser)SecurityContexts.getContext().requireUser();
		return sysRoleRestSupport.getUsersBySysRoleId(id, request, user);
	}

	@ApiOperation(value = "为角色绑定用户")
	@RequestMapping(value = "/sysroles/{id}/bindUsers", method = RequestMethod.POST)
	public void bindUsers4SysRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		SysUser user = (SysUser)SecurityContexts.getContext().requireUser();
		sysRoleRestSupport.bindUsers4SysRole(id, request, user);
	}

	@ApiOperation(value = "为角色解绑用户")
	@RequestMapping(value = "/sysroles/{id}/unBindUsers", method = RequestMethod.POST)
	public void unBindUsers4SysRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		SysUser user = (SysUser)SecurityContexts.getContext().requireUser();
		sysRoleRestSupport.unBindUsers4SysRole(id, request, user);
	}
}
