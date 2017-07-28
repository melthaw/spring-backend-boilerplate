package in.clouthink.daas.sbb.openapi.controller;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.openapi.support.MyProfileRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("我的个人资料")
@RestController
@RequestMapping("/api")
public class MyProfileRestController {

	@Autowired
	private MyProfileRestSupport myProfileRestSupport;

	@ApiOperation(value = "查看我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.GET)
	public AppUserDetail getUserProfile() {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		return myProfileRestSupport.getUserProfile(user);
	}

	@ApiOperation(value = "修改我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.POST)
	public void updateUserProfile(@RequestBody ChangeMyProfileParameter request) {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		myProfileRestSupport.updateUserProfile(request, user);
	}

	@ApiOperation(value = "修改我的头像信息")
	@RequestMapping(value = "/my/avatar", method = RequestMethod.POST)
	public void updateUserAvatar(@RequestBody String imageId) {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		myProfileRestSupport.updateUserAvatar(imageId, user);
	}

	@ApiOperation(value = "删除我的头像信息")
	@RequestMapping(value = "/my/avatar", method = RequestMethod.DELETE)
	public void deleteUserAvatar() {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		myProfileRestSupport.updateUserAvatar(null, user);
	}

	@ApiOperation(value = "修改我的密码")
	@RequestMapping(value = "/my/password", method = RequestMethod.POST)
	public void changeMyPassword(@RequestBody ChangeMyPasswordRequest request) {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		myProfileRestSupport.changeMyPassword(request, user);
	}

}
