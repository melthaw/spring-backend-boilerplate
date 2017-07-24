package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.dashboard.rest.dto.*;
import in.clouthink.daas.sbb.dashboard.rest.support.SysUserRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Api("系统用户管理")
@RestController
@RequestMapping("/api")
public class SysUserRestController {

	@Autowired
	private SysUserRestSupport sysUserRestSupport;

	@ApiOperation(value = "系统用户列表,支持分页,支持动态查询（用户名等）")
	@RequestMapping(value = "/sysusers", method = RequestMethod.GET)
	public Page<SysUserSummary> listSysUsers(SysUserQueryParameter queryRequest) {
		return sysUserRestSupport.listSysUsers(queryRequest);
	}

	@ApiOperation(value = "查看系统用户详情")
	@RequestMapping(value = "/sysusers/{id}", method = RequestMethod.GET)
	public SysUserDetail getSysUserDetail(@PathVariable String id) {
		return sysUserRestSupport.getSysUserDetail(id);
	}

	@ApiOperation(value = "新增系统用户（基本资料部分）")
	@RequestMapping(value = "/sysusers", method = RequestMethod.POST)
	public String createSysUser(@RequestBody SaveSysUserParameter request) {
		return sysUserRestSupport.createSysUser(request).getId();
	}

	@ApiOperation(value = "修改系统用户基本资料")
	@RequestMapping(value = "/sysusers/{id}", method = RequestMethod.POST)
	public void updateSysUser(@PathVariable String id, @RequestBody SaveSysUserParameter request) {
		sysUserRestSupport.updateSysUser(id, request);
	}

	@ApiOperation(value = "删除系统用户")
	@RequestMapping(value = "/sysusers/{id}", method = RequestMethod.DELETE)
	public void deleteSysUser(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		sysUserRestSupport.deleteSysUser(id, user);
	}

	@ApiOperation(value = "修改系统用户密码")
	@RequestMapping(value = "/sysusers/{id}/password", method = RequestMethod.POST)
	public void changePassword(@PathVariable String id, @RequestBody ChangePasswordRequest request) {
		sysUserRestSupport.changePassword(id, request);
	}

	@ApiOperation(value = "启用系统用户")
	@RequestMapping(value = "/sysusers/{id}/enable", method = RequestMethod.POST)
	public void enableSysUser(@PathVariable String id) {
		sysUserRestSupport.enableSysUser(id);
	}

	@ApiOperation(value = "禁用系统用户,用户被禁用后不能使用系统")
	@RequestMapping(value = "/sysusers/{id}/disable", method = RequestMethod.POST)
	public void disableSysUser(@PathVariable String id) {
		sysUserRestSupport.disableSysUser(id);
	}

	@ApiOperation(value = "锁定系统用户,用户被锁定后不能使用系统")
	@RequestMapping(value = "/sysusers/{id}/lock", method = RequestMethod.POST)
	public void lockSysUser(@PathVariable String id) {
		sysUserRestSupport.lockSysUser(id);
	}

	@ApiOperation(value = "取消锁定系统用户")
	@RequestMapping(value = "/sysusers/{id}/unlock", method = RequestMethod.POST)
	public void unlockSysUser(@PathVariable String id) {
		sysUserRestSupport.unlockSysUser(id);
	}

}
