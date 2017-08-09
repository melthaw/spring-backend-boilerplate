package in.clouthink.daas.sbb.account.rest.controller;

import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.rest.support.AppRoleRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("应用扩展角色管理")
@RestController
@RequestMapping("/api")
public class AppRoleRestControler {

	@Autowired
	private AppRoleRestSupport appRoleRestSupport;

	@ApiOperation(value = "获取新增角色")
	@RequestMapping(value = "/approles", method = RequestMethod.GET)
	public Page<RoleSummary> getAppRoles(RoleQueryParameter request) {
		return appRoleRestSupport.getAppRoles(request);
	}

	@ApiOperation(value = "获取新增角色(不分页)")
	@RequestMapping(value = "/approles/list", method = RequestMethod.GET)
	public List<RoleSummary> getAppRolesList() {
		return appRoleRestSupport.getAppRolesList();
	}

	@ApiOperation(value = "获取新增角色对应用户")
	@RequestMapping(value = "/approles/{id}/users", method = RequestMethod.GET)
	public Page<UserSummary> getUsersByAppRoleId(@PathVariable String id, UserQueryParameter request) {
		return appRoleRestSupport.getUsersByAppRoleId(id, request);
	}

	@ApiOperation(value = "新增角色")
	@RequestMapping(value = "/approles", method = RequestMethod.POST)
	public String createAppRole(@RequestBody SaveRoleParameter request) {
		return appRoleRestSupport.createAppRole(request).getId();
	}

	@ApiOperation(value = "更新角色")
	@RequestMapping(value = "/approles/{id}", method = RequestMethod.POST)
	public void updateAppRole(@PathVariable String id, @RequestBody SaveRoleParameter request) {
		appRoleRestSupport.updateAppRole(id, request);
	}

	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "/approles/{id}", method = RequestMethod.DELETE)
	public void deleteAppRole(@PathVariable String id) {
		appRoleRestSupport.deleteAppRole(id);
	}

	@ApiOperation(value = "为角色绑定用户")
	@RequestMapping(value = "/approles/{id}/bindUsers", method = RequestMethod.POST)
	public void bindUsers4AppRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		appRoleRestSupport.bindUsers4AppRole(id, request);
	}

	@ApiOperation(value = "为角色解绑用户")
	@RequestMapping(value = "/approles/{id}/unBindUsers", method = RequestMethod.POST)
	public void unBindUsers4AppRole(@PathVariable String id, @RequestBody UsersForRoleParameter request) {
		appRoleRestSupport.unBindUsers4AppRole(id, request);
	}

}
