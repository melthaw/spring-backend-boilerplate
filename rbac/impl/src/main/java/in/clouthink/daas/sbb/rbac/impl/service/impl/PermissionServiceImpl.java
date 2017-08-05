package in.clouthink.daas.sbb.rbac.impl.service.impl;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.rbac.impl.model.ResourceRoleRelationship;
import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.impl.repository.ResourceRoleRelationshipRepository;
import in.clouthink.daas.sbb.rbac.impl.service.support.RbacUtils;
import in.clouthink.daas.sbb.rbac.model.*;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import in.clouthink.daas.sbb.rbac.support.matcher.ResourceMatchers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
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
	public List<Role> getGrantedRoles(String resourceCode) {
		List<ResourceRoleRelationship> relationshipList = resourceRoleRelationshipRepository.findByResourceCode(
				resourceCode);
		if (relationshipList == null) {
			return Collections.unmodifiableList(Collections.emptyList());
		}

		return relationshipList.stream()
							   .map(relationship -> relationship.getRoleCode())
							   .collect(Collectors.toSet())
							   .stream()
							   .map(roleCode -> {
								   ExtRole role = roleService.findByCode(roleCode);
								   TypedRole result = TypedRole.newSysRole();
								   result.setCode(role.getCode());
								   result.setName(role.getName());
								   return result;
							   })
							   .collect(Collectors.toList());
	}

	@Override
	public List<Role> getGrantedRoles(Resource resource) {
		return getGrantedRoles(resource.getCode());
	}

	@Override
	public Permission getPermission(String resourceCode, GrantedAuthority role) {
		return null;
	}

	public List<Resource> getGrantedResources(GrantedAuthority role, String resourceType) {
		return null;
	}

	public List<Resource> getGrantedResources(List<GrantedAuthority> roles, String resourceType) {
		return null;
	}

	@Override
	public List<Resource> getGrantedResources(GrantedAuthority role) {
		if (role == null) {
			return null;
		}

		return getGrantedResources(Arrays.asList(role));
	}

	@Override
	public List<Resource> getGrantedResources(List<GrantedAuthority> roles) {
		if (roles == null || roles.isEmpty()) {
			return null;
		}

		for (GrantedAuthority role : roles) {
			if (SysRole.ROLE_ADMIN == role) {
				List<Resource> result = new ArrayList<>();
				resourceService.getRootResources()
							   .stream()
							   .forEach(resource -> result.add((Resource) resource));
				return result;
			}
		}

		return doGetAllowedResources(resourceService.getRootResources(), roles);
	}


	private List<Resource> doGetAllowedResources(List<? extends Resource> existedResources,
															 List<GrantedAuthority> roles) {
		List<Resource> result = new ArrayList<>();
		existedResources.stream().forEach(resource -> {
			if (resource.isOpen()) {
				result.add((Resource) resource);
				return;
			}

			boolean granted = false;
			String resourceCode = resource.getCode();
			for (GrantedAuthority role : roles) {
				ResourceRoleRelationship resourceRoleRelationship = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
						resourceCode,
						RbacUtils.buildRoleCode(role));
				if (resourceRoleRelationship != null) {
					granted = true;
					break;
				}
			}

			if (granted) {
				DefaultResource resourceWithChildren = new DefaultResource();
				BeanUtils.copyProperties(resource, resourceWithChildren, "children");
//				if (((Resource) resource).hasChildren()) {
//					resourceWithChildren.setChildren(doGetAllowedResources(((Resource) resource).getChildren(),
//																		   roles));
//				}
//				//虚父节点有权限,但是子节点无权限,虚父节点不需要返回
//				if (resourceWithChildren.isVirtual() && !resourceWithChildren.hasChildren()) {
//					return;
//				}
				result.add(resourceWithChildren);
			}
		});

		return result;
	}

	@Override
	public List<Resource> getGrantedResources(GrantedAuthority role, ResourceMatcher filter) {
		return null;
	}

	@Override
	public List<Resource> getGrantedResources(List<GrantedAuthority> roles, ResourceMatcher filter) {
		return null;
	}

	@Override
	public boolean isGranted(String resourceCode, GrantedAuthority role) {
		if (SysRole.ROLE_ADMIN.getCode().equalsIgnoreCase(role.getAuthority())) {
			return true;
		}
		ResourceRoleRelationship result = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(resourceCode,
																										   RbacUtils.buildRoleCode(
																												   role));
		return result != null;
	}

	@Override
	public boolean isGranted(Resource resource, GrantedAuthority role) {
		if (SysRole.ROLE_ADMIN.getCode().equalsIgnoreCase(role.getAuthority())) {
			return true;
		}
		ResourceRoleRelationship result = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(resource.getCode(),
																										   RbacUtils.buildRoleCode(
																												   role));
		return (result != null);
	}

}
