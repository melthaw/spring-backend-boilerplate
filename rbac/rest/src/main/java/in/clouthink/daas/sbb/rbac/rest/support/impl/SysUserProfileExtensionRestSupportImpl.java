package in.clouthink.daas.sbb.rbac.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.rbac.rest.dto.MenuSummary;
import in.clouthink.daas.sbb.rbac.rest.support.SysUserProfileExtensionRestSupport;
import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SysUserProfileExtensionRestSupportImpl implements SysUserProfileExtensionRestSupport {

	@Autowired
	private PermissionService permissionService;

	@Override
	public List<MenuSummary> getUserGrantedMenus(User user) {
		List<ResourceWithChildren> resourceWithChildren = permissionService.getGrantedResources((List) user.getAuthorities());
		return resourceWithChildren.stream().map(MenuSummary::from).collect(Collectors.toList());
	}

}
