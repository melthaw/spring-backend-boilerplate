package in.clouthink.daas.sbb.rbac.impl.service.impl;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.rbac.impl.model.ResourceRoleRelationship;
import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.impl.repository.ResourceRoleRelationshipRepository;
import in.clouthink.daas.sbb.rbac.impl.service.support.RbacUtils;
import in.clouthink.daas.sbb.rbac.model.*;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * the default permission service impl.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceRoleRelationshipRepository resourceRoleRelationshipRepository;

	@Override
	public Permission getPermission(String resourceCode, GrantedAuthority authority) {
		return getPermission(resourceCode, Arrays.asList(authority));
	}

	@Override
	public Permission getPermission(String resourceCode, List<GrantedAuthority> roles) {
		return Optional.ofNullable(roles.stream()
										.map(role -> resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
												resourceCode,
												RbacUtils.buildRoleCode(role)))
										.reduce(null, (acc, item) -> {
											if (item == null) {
												return acc;
											}

											if (item == null && acc != null) {
												return acc;
											}

											if (acc == null && item != null) {
												return item;
											}

											item.getAllowedActions().stream().forEach(actionCode -> {
												if (!acc.getAllowedActions().contains(actionCode)) {
													acc.getAllowedActions().add(actionCode);
												}
											});

											return acc;
										}))

					   .map(resourceRoleRelationship -> {
						   Resource resource = resourceService.findByCode(resourceCode);

						   DefaultPermission result = new DefaultPermission();
						   result.setResource(resource);
						   result.setGrantedActions(resource.getActions()
															.stream()
															.filter(action -> resourceRoleRelationship.getAllowedActions()
																									  .contains(action.getCode()))
															.collect(Collectors.toList())
															.toArray(new Action[0]));
						   return result;
					   })

					   .orElse(null);
	}

	@Override
	public List<Role> getGrantedRoles(String resourceCode) {
		return resourceRoleRelationshipRepository.findListByResourceCode(resourceCode)
												 .stream()
												 .map(relationship -> relationship.getRoleCode())
												 .collect(Collectors.toSet())
												 .stream()
												 .map(roleCode -> {
													 AppRole role = roleService.findByCode(roleCode);
													 TypedRole result = TypedRole.newSysRole();
													 result.setCode(role.getCode());
													 result.setName(role.getName());
													 return result;
												 })
												 .collect(Collectors.toList());
	}

	@Override
	public List<Resource> getGrantedResources(GrantedAuthority role) {
		return Optional.ofNullable(role)
					   .map(r -> getGrantedResources(Arrays.asList(r)))
					   .orElse(Collections.emptyList());
	}

	@Override
	public List<Resource> getGrantedResources(List<GrantedAuthority> roles) {
		return roles.stream()
					.filter(role -> SysRole.ROLE_ADMIN == role)
					.findFirst()
					.map(role -> resourceService.getFlattenResources())
					.orElseGet(() -> doGetGrantedResources(resourceService.getFlattenResources(), roles));
	}

	private List<Resource> doGetGrantedResources(List<? extends Resource> existedResources,
												 List<GrantedAuthority> roles) {
		List<Resource> result = new ArrayList<>();

		//always return the open resource
		existedResources.stream().filter(res -> res.isOpen()).forEach(res -> result.add(res));
		//and return the granted resource
		existedResources.stream().filter(res -> !res.isOpen()).forEach(resource -> {
			for (GrantedAuthority role : roles) {
				ResourceRoleRelationship resourceRoleRelationship = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
						resource.getCode(),
						RbacUtils.buildRoleCode(role));
				if (resourceRoleRelationship != null) {
					result.add(resource);
					break;
				}
			}
		});

		return result;
	}

	@Override
	public List<Resource> getGrantedResources(GrantedAuthority role, ResourceMatcher filter) {
		return getGrantedResources(Arrays.asList(role), filter);
	}

	@Override
	public List<Resource> getGrantedResources(List<GrantedAuthority> roles, ResourceMatcher filter) {
		return getGrantedResources(roles).stream()
										 .filter(resource -> filter.matched(resource))
										 .collect(Collectors.toList());
	}

	@Override
	public List<Action> getGrantedActions(String resourceCode, List<GrantedAuthority> roles) {
		Resource resource = resourceService.findByCode(resourceCode);
		if (resource == null) {
			return Collections.emptyList();
		}

		Set<String> grantedActionCodes = roles.stream()
											  .flatMap(role -> Optional.of(resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
													  resourceCode,
													  RbacUtils.buildRoleCode(role)))
																	   .map(relationship -> relationship.getAllowedActions()
																										.stream())
																	   .orElse(null))
											  .collect(Collectors.toSet());

		return resource.getActions()
					   .stream()
					   .filter(action -> grantedActionCodes.contains(action.getCode()))
					   .collect(Collectors.toList());
	}

	@Override
	public boolean isGranted(String resourceCode, GrantedAuthority role) {
		if (SysRole.ROLE_ADMIN.getCode().equalsIgnoreCase(role.getAuthority())) {
			return true;
		}

		return null !=
			   resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(resourceCode,
																				RbacUtils.buildRoleCode(role));
	}

	@Override
	public boolean isGranted(String resourceCode, String actionCode, GrantedAuthority role) {
		if (SysRole.ROLE_ADMIN.getCode().equalsIgnoreCase(role.getAuthority())) {
			return true;
		}

		return Optional.ofNullable(resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(resourceCode,
																									RbacUtils.buildRoleCode(
																											role)))
					   .map(resourceRoleRelationship -> resourceRoleRelationship.getAllowedActions()
																				.contains(actionCode))
					   .orElse(Boolean.FALSE);
	}

}
