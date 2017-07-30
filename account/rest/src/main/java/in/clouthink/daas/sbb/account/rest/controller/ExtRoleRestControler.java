package in.clouthink.daas.sbb.account.rest.controller;

import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.rest.support.ExtRoleRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("角色管理")
@RestController
@RequestMapping("/api/roles")
public class ExtRoleRestControler {

	@Autowired
	private ExtRoleRestSupport extRoleRestSupport;

	@ApiOperation(value = "获取新增角色")
	@RequestMapping(value = "/extroles", method = RequestMethod.GET)
	public Page<RoleSummary> getAppRoles(RoleQueryParameter request) {
		return extRoleRestSupport.getAppRoles(request);
	}

	@ApiOperation(value = "获取新增角色(不分页)")
	@RequestMapping(value = "/extroles/list", method = RequestMethod.GET)
	public List<RoleSummary> getAppRolesList() {
		return extRoleRestSupport.getAppRolesList();
	}


	@ApiOperation(value = "获取新增角色对应用户")
	@RequestMapping(value = "/extroles/{id}/users", method = RequestMethod.GET)
	public Page<UserSummary> getUsersByAppRoleId(@PathVariable String id, UserQueryParameter request) {
		return extRoleRestSupport.getUsersByAppRoleId(id, request);
	}

	@ApiOperation(value = "新增角色")
	@RequestMapping(value = "/extroles", method = RequestMethod.POST)
	public String createAppRole(@RequestBody SaveRoleParameter request) {
		return extRoleRestSupport.createAppRole(request).getId();
	}

	@ApiOperation(value = "更新角色")
	@RequestMapping(value = "/extroles/{id}", method = RequestMethod.POST)
	public void updateAppRole(@PathVariable String id, @RequestBody SaveRoleParameter request) {
		extRoleRestSupport.updateAppRole(id, request);
	}

	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "/extroles/{id}", method = RequestMethod.DELETE)
	public void deleteAppRole(@PathVariable String id) {
		extRoleRestSupport.deleteAppRole(id);
	}

	@ApiOperation(value = "为角色绑定用户")
	@RequestMapping(value = "/extroles/{id}/bindUsers", method = RequestMethod.POST)
	public void bindUsers4AppRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		extRoleRestSupport.bindUsers4AppRole(id, request);
	}

	@ApiOperation(value = "为角色解绑用户")
	@RequestMapping(value = "/extroles/{id}/unBindUsers", method = RequestMethod.POST)
	public void unBindUsers4AppRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		extRoleRestSupport.unBindUsers4AppRole(id, request);
	}

}
