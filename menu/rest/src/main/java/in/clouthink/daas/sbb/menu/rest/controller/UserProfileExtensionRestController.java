package in.clouthink.daas.sbb.menu.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.menu.rest.dto.Menu;
import in.clouthink.daas.sbb.menu.rest.support.UserProfileExtensionRestSupport;
import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对 SysUserProfileRestController 的扩展,增加了获取菜单
 */
@Api("我的个人资料")
@RestController
@RequestMapping("/api")
public class UserProfileExtensionRestController {

	@Autowired
	private UserProfileExtensionRestSupport userProfileRestSupport;

	@ApiOperation(value = "查看我的菜单(已授权的)")
	@RequestMapping(value = "/my/menus", method = RequestMethod.GET)
	public List<Menu> getGrantedMenus() {
		User user = (User) SecurityContexts.getContext().requireUser();
		return userProfileRestSupport.getGrantedMenus(user);
	}

	@ApiOperation(value = "查看我的菜单(已授权的)")
	@RequestMapping(value = "/my/menus/{code}/actions", method = RequestMethod.GET)
	public List<Action> getGrantedActions(@PathVariable String code) {
		User user = (User) SecurityContexts.getContext().requireUser();
		return userProfileRestSupport.getGrantedActions(code, user);
	}

}
