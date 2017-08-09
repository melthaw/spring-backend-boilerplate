package in.clouthink.daas.sbb.rbac.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.RoleType;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.impl.service.support.RbacUtils;
import in.clouthink.daas.sbb.rbac.impl.service.support.ResourceRoleRelationshipService;
import in.clouthink.daas.sbb.rbac.model.TypedCode;
import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.service.ResourceCacheService;
import in.clouthink.daas.sbb.rbac.rest.support.PermissionRestSupport;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import in.clouthink.daas.sbb.rbac.support.parser.RoleCodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionRestSupportImpl implements PermissionRestSupport {

	private RoleCodeParser roleCodeParser = new RoleCodeParser();

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceCacheService resourceCacheService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ResourceRoleRelationshipService resourceRoleRelationshipService;

	@Override
	public List<PrivilegedResourceWithChildren> listGrantedResources(String roleCode) {
		//granted resource codes & action codes
		Map<String,Set<String>> resourceCodes =

				resourceRoleRelationshipService.listGrantedResources(roleCode)
											   .stream()
											   .collect(Collectors.toMap(resource -> resource.getResourceCode(),
																		 resource -> resource.getAllowedActions()
																							 .stream()
																							 .collect(Collectors.toSet())));

		List<PrivilegedResourceWithChildren> result = resourceCacheService.listResources(false);

		processChildren(result, resourceCodes);

		return result;
	}

	private void processChildren(List<PrivilegedResourceWithChildren> result, Map<String,Set<String>> resourceCodes) {
		result.stream().forEach(resourceWithChildren -> {
			resourceWithChildren.setGranted(resourceCodes.containsKey(resourceWithChildren.getCode()));
			resourceWithChildren.getActions().stream().forEach(action -> {
				Set<String> actionCodes = resourceCodes.get(resourceWithChildren.getCode());
				action.setGranted(actionCodes != null && actionCodes.contains(action.getCode()));
			});

			processChildren(resourceWithChildren.getChildren(), resourceCodes);
		});
	}

	@Override
	public List<TypedRole> listGrantedRoles(String code) {
		return resourceRoleRelationshipService.listGrantedRoles(code)
											  .stream()
											  .map(authority -> RbacUtils.convertToTypedRole(authority))
											  .collect(Collectors.toList());
	}

	@Override
	public void grantResourcesToRole(String typedRoleCode, GrantResourceParameter parameter) {
		TypedCode typedCode = roleCodeParser.parse(typedRoleCode);
		String resourceCode = parameter.getResourceCode();
		String[] actionCodes = parameter.getActionCodes();

		if (RoleType.APP_ROLE.name().equals(typedCode.getType())) {
			AppRole appRole = roleService.findByCode(typedCode.getCode());
			if (appRole != null) {
				resourceRoleRelationshipService.grantPermission(resourceCode, actionCodes, appRole);
			}
		}
		else if (RoleType.SYS_ROLE.name().equals(typedCode.getType())) {
			SysRole sysRole = SysRole.valueOf(typedCode.getCode());
			if (sysRole != null) {
				resourceRoleRelationshipService.grantPermission(resourceCode, actionCodes, sysRole);
			}
		}
	}

	@Override
	public void revokeResourcesFromRole(String typedRoleCode, String resourceCode) {
		TypedCode typedCode = roleCodeParser.parse(typedRoleCode);
		if (RoleType.APP_ROLE.name().equals(typedCode.getType())) {
			AppRole appRole = roleService.findByCode(typedCode.getCode());
			if (appRole != null) {
				resourceRoleRelationshipService.revokePermission(resourceCode, appRole);
			}
		}
		else if (RoleType.SYS_ROLE.name().equals(typedCode.getType())) {
			SysRole sysRole = SysRole.valueOf(typedCode.getCode());
			if (sysRole != null) {
				resourceRoleRelationshipService.revokePermission(resourceCode, sysRole);
			}
		}
	}

}
