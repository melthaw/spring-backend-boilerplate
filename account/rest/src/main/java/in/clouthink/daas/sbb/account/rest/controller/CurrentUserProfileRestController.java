package in.clouthink.daas.sbb.account.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserProfile;
import in.clouthink.daas.sbb.account.rest.support.UserProfileRestSupport;
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
public class CurrentUserProfileRestController {

	@Autowired
	private UserProfileRestSupport currentUserProfileRestSupport;

//	@Autowired
//	private StorageService attachmentService;

	@ApiOperation(value = "查看我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.GET)
	public UserProfile getUserProfile() {
		User user = (User) SecurityContexts.getContext().requireUser();
		return currentUserProfileRestSupport.getUserProfile(user);
	}

	@ApiOperation(value = "修改我的个人资料")
	@RequestMapping(value = "/my/profile", method = RequestMethod.POST)
	public void updateUserProfile(@RequestBody ChangeMyProfileParameter request) {
		User user = (User) SecurityContexts.getContext().requireUser();
		currentUserProfileRestSupport.updateUserProfile(request, user);
	}

	@ApiOperation(value = "修改我的头像信息")
	@RequestMapping(value = "/my/avatar", method = RequestMethod.POST)
	public void updateUserAvatar(@RequestBody String imageId) {
		User user = (User) SecurityContexts.getContext().requireUser();
		currentUserProfileRestSupport.updateUserAvatar(imageId, user);
	}

	@ApiOperation(value = "删除我的头像信息")
	@RequestMapping(value = "/my/avatar", method = RequestMethod.DELETE)
	public void deleteUserAvatar() {
		User user = (User) SecurityContexts.getContext().requireUser();
		currentUserProfileRestSupport.updateUserAvatar(null, user);
	}

	@ApiOperation(value = "修改我的密码")
	@RequestMapping(value = "/my/password", method = RequestMethod.POST)
	public void changeMyPassword(@RequestBody ChangeMyPasswordRequest request) {
		User user = (User) SecurityContexts.getContext().requireUser();
		currentUserProfileRestSupport.changeMyPassword(request, user);
	}

}
