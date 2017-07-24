package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.AppUserQueryParameter;
import in.clouthink.daas.sbb.account.dto.AppUserSummary;
import in.clouthink.daas.sbb.dashboard.rest.support.AppUserRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("应用注册用户管理")
@RestController
@RequestMapping("/api")
public class AppUserRestController {

	@Autowired
	private AppUserRestSupport appUserRestSupport;

	@ApiOperation(value = "应用用户列表,支持分页,支持动态查询（用户名等）")
	@RequestMapping(value = "/appusers", method = RequestMethod.GET)
	public Page<AppUserSummary> listAppUsers(AppUserQueryParameter queryRequest) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return appUserRestSupport.listAppUsers(queryRequest);
	}

	@ApiOperation(value = "查看应用用户详情")
	@RequestMapping(value = "/appusers/{id}", method = RequestMethod.GET)
	public AppUserDetail getAppUserDetail(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return appUserRestSupport.getAppUserDetail(id);
	}

	@ApiOperation(value = "启用应用用户")
	@RequestMapping(value = "/appusers/{id}/enable", method = RequestMethod.POST)
	public void enableAppUser(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		appUserRestSupport.enableAppUser(id);
	}

	@ApiOperation(value = "禁用应用用户,用户被禁用后不能使用系统")
	@RequestMapping(value = "/appusers/{id}/disable", method = RequestMethod.POST)
	public void disableAppUser(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		appUserRestSupport.disableAppUser(id);
	}

	@ApiOperation(value = "锁定应用用户,用户被锁定后不能使用系统")
	@RequestMapping(value = "/appusers/{id}/lock", method = RequestMethod.POST)
	public void lockAppUser(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		appUserRestSupport.lockAppUser(id);
	}

	@ApiOperation(value = "取消锁定应用用户")
	@RequestMapping(value = "/appusers/{id}/unlock", method = RequestMethod.POST)
	public void unlockAppUser(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		appUserRestSupport.unlockAppUser(id);
	}


}
