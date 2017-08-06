package in.clouthink.daas.sbb.rbac.impl.service.support;


import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 *
 */
public interface ResourceRoleRelationshipService {

	Resource findResourceByCode(String code);

	List<? extends Resource> listRootResources();

	List<? extends Resource> listResourceChildren(String resourceCode);

	List<? extends Resource> listResourceChildren(Resource parent);

	List<? extends Resource> listAllowedResource(GrantedAuthority role);

	List<? extends Resource> listAllowedResource(String roleCode);

	List<String> listAllowedRoleCodes(String resourceCode);

	List<String> listAllowedRoleCodes(Resource resource);

	List<GrantedAuthority> listAllowedRoles(String resourceCode);

	List<GrantedAuthority> listAllowedRoles(Resource resource);

	void bindResourceAndRole(String resourceCode, String[] actionCodes, GrantedAuthority role);

	void unbindResourceAndRole(String resourceCode, GrantedAuthority role);

}
