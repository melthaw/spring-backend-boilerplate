package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.service.SysUserProfileService;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.dashboard.rest.dto.MenuSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserProfile;
import in.clouthink.daas.sbb.dashboard.rest.support.SysUserProfileRestSupport;
import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SysUserProfileRestSupportImpl implements SysUserProfileRestSupport {

	@Autowired
	private SysUserProfileService userProfileService;

	@Autowired
	private PermissionService rbacService;

	@Override
	public SysUserProfile getUserProfile(SysUser user) {
		SysUser result = userProfileService.findUserById(user.getId());
		return SysUserProfile.from(result);
	}

	@Override
	public void updateUserProfile(ChangeMyProfileParameter request, SysUser byWho) {
		userProfileService.changeUserProfile(byWho.getId(), request);
	}

	@Override
	public void changeMyPassword(ChangeMyPasswordRequest request, SysUser byWho) {
		userProfileService.changeUserPassword(byWho.getId(), request.getOldPassword(), request.getNewPassword());
	}

	@Override
	public List<MenuSummary> getUserGrantedMenus(SysUser user) {
		List<ResourceWithChildren> resourceWithChildren = rbacService.getGrantedResources((List) user.getAuthorities());
		return resourceWithChildren.stream().map(MenuSummary::from).collect(Collectors.toList());
	}

}
