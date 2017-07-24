package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.dashboard.rest.dto.MenuSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserProfile;
import in.clouthink.daas.sbb.dashboard.rest.support.SysUserProfileRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@Api("我的个人资料")
@RestController
@RequestMapping("/api")
public class SysUserProfileRestController {

	@Autowired
	private SysUserProfileRestSupport userProfileRestSupport;

	@ApiOperation(value = "查看我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.GET)
	public SysUserProfile getUserProfile() {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return userProfileRestSupport.getUserProfile(user);
	}

	@ApiOperation(value = "修改我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.POST)
	public void updateUserProfile(@RequestBody ChangeMyProfileParameter request) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		userProfileRestSupport.updateUserProfile(request, user);
	}

	@ApiOperation(value = "修改我的密码")
	@RequestMapping(value = "/my/password", method = RequestMethod.POST)
	public void changeMyPassword(@RequestBody ChangeMyPasswordRequest request) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		userProfileRestSupport.changeMyPassword(request, user);
	}

	@ApiOperation(value = "查看我的菜单(已授权的)")
	@RequestMapping(value = "/my/menus", method = RequestMethod.GET)
	public List<MenuSummary> getUserGrantedMenus() {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return userProfileRestSupport.getUserGrantedMenus(user);
	}

}
