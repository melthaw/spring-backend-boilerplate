package in.clouthink.daas.sbb.openapi.controller;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.openapi.support.AppUserProfileRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("用户的个人资料")
@RestController
@RequestMapping("/api/users")
public class AppUserProfileRestController {

	@Autowired
	private AppUserProfileRestSupport appUserProfileRestSupport;

	@ApiOperation(value = "查看用户的个人资料")
	@RequestMapping(value = "/{id}/profile", method = RequestMethod.GET)
	public AppUserDetail getUserProfile(@PathVariable String id) {
		AppUser user = (AppUser)SecurityContexts.getContext().requireUser();
		return appUserProfileRestSupport.getUserProfile(id, user);
	}

}
