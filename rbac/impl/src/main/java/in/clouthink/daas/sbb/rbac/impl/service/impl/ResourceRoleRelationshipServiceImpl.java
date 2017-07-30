package in.clouthink.daas.sbb.rbac.impl.service.impl;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.rbac.impl.model.ResourceRoleRelationship;
import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.impl.repository.ResourceRoleRelationshipRepository;
import in.clouthink.daas.sbb.rbac.impl.service.ResourceRoleRelationshipService;
import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.TypedCode;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import in.clouthink.daas.sbb.rbac.support.parser.RoleCodeParser;
import in.clouthink.daas.sbb.rbac.support.parser.RoleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Service
public class ResourceRoleRelationshipServiceImpl implements ResourceRoleRelationshipService {

	private RoleParser<TypedCode> roleParser = new RoleCodeParser();

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceRoleRelationshipRepository resourceRoleRelationshipRepository;

	@Autowired
	private RoleService roleService;

	public void setRoleParser(RoleParser<TypedCode> roleParser) {
		this.roleParser = roleParser;
	}

	@Override
	public Resource findResourceByCode(String code) {
		return resourceService.findByCode(code);
	}

	@Override
	public List<? extends Resource> listRootResources() {
		return resourceService.getRootResources();
	}

	@Override
	public List<? extends Resource> listResourceChildren(String resourceCode) {
		return listResourceChildren(resourceService.findByCode(resourceCode));
	}

	@Override
	public List<? extends Resource> listResourceChildren(Resource parent) {
		return resourceService.getResourceChildren(parent);
	}

	@Override
	public List<? extends Resource> listAllowedResource(GrantedAuthority role) {
		String roleCode = RbacUtils.buildRoleCode(role);
		return listAllowedResource(roleCode);
	}

	@Override
	public List<? extends Resource> listAllowedResource(String roleCode) {
		return resourceRoleRelationshipRepository.findByRoleCode(roleCode)
												 .stream()
												 .map(relationship -> resourceService.findByCode(relationship.getResourceCode()))
												 .collect(Collectors.toList());
	}

	@Override
	public List<String> listAllowedRoleCodes(String resourceCode) {
		return resourceRoleRelationshipRepository.findByResourceCode(resourceCode)
												 .stream()
												 .map(relationship -> relationship.getRoleCode())
												 .collect(Collectors.toList());
	}

	@Override
	public List<String> listAllowedRoleCodes(Resource resource) {
		return listAllowedRoleCodes(resource.getCode());
	}

	@Override
	public List<GrantedAuthority> listAllowedRoles(String resourceCode) {
		return resourceRoleRelationshipRepository.findByResourceCode(resourceCode).stream().map(relationship -> {
			String role = relationship.getRoleCode();
			TypedCode typedCode = roleParser.parse(role.toUpperCase());
			if (TypedRole.isAppRole(typedCode.getType())) {
				ExtRole appRole = roleService.findByCode(typedCode.getCode());
				if (appRole != null) {
					return appRole;
				}
			}
			else if (TypedRole.isSysRole(typedCode.getType())) {
				SysRole sysRole = SysRole.valueOf(typedCode.getCode());
				if (sysRole != null) {
					return sysRole;
				}
			}
			return null;
		}).collect(Collectors.toList());
	}

	@Override
	public List<GrantedAuthority> listAllowedRoles(Resource resource) {
		return listAllowedRoles(resource.getCode());
	}

	@Override
	public void bindResourceAndRole(String resourceCode, GrantedAuthority role) {
		String roleCode = RbacUtils.buildRoleCode(role);
		Resource resource = resourceService.findByCode(resourceCode);
		if (resource == null) {
			return;
		}
		ResourceRoleRelationship resourceRoleRelationship = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
				resourceCode,
				roleCode);
		if (resourceRoleRelationship == null) {
			resourceRoleRelationship = new ResourceRoleRelationship();
			resourceRoleRelationship.setResourceCode(resourceCode);
			resourceRoleRelationship.setRoleCode(roleCode);
			resourceRoleRelationshipRepository.save(resourceRoleRelationship);
		}

		Resource parentResource = resource.getParent();
		if (parentResource == null) {
			return;
		}

		//The parent will be granted automatically if the child is granted
		ResourceRoleRelationship parentResourceRoleRelationship = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
				parentResource.getCode(),
				roleCode);
		if (parentResourceRoleRelationship == null) {
			parentResourceRoleRelationship = new ResourceRoleRelationship();
			parentResourceRoleRelationship.setResourceCode(parentResource.getCode());
			parentResourceRoleRelationship.setRoleCode(roleCode);
			resourceRoleRelationshipRepository.save(parentResourceRoleRelationship);
		}
	}

	@Override
	public void unbindResourceAndRole(String resourceCode, GrantedAuthority role) {
		String roleCode = RbacUtils.buildRoleCode(role);
		ResourceRoleRelationship resourceRoleRelationship = resourceRoleRelationshipRepository.findByResourceCodeAndRoleCode(
				resourceCode,
				roleCode);
		if (resourceRoleRelationship == null) {
			return;
		}
		resourceRoleRelationshipRepository.delete(resourceRoleRelationship);

	}

}
