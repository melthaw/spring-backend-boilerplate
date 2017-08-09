package in.clouthink.daas.sbb.rbac.impl.service.support;


import in.clouthink.daas.sbb.rbac.impl.model.ResourceRoleRelationship;
import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 *
 */
public interface ResourceRoleRelationshipService {

	/**
	 * @param role
	 * @return
	 */
	List<ResourceRoleRelationship> listGrantedResources(GrantedAuthority role);

	/**
	 * @param roleCode
	 * @return
	 */
	List<ResourceRoleRelationship> listGrantedResources(String roleCode);

	/**
	 * @param resourceCode
	 * @return typed role codes
	 */
	List<String> listGrantedRoleCodes(String resourceCode);

	/**
	 * @param resource
	 * @return typed role codes
	 */
	List<String> listGrantedRoleCodes(Resource resource);

	/**
	 * @param resourceCode
	 * @return
	 */
	List<GrantedAuthority> listGrantedRoles(String resourceCode);

	/**
	 * @param resource
	 * @return
	 */
	List<GrantedAuthority> listGrantedRoles(Resource resource);

	/**
	 * @param resourceCode
	 * @param actionCodes
	 * @param role
	 */
	void grantPermission(String resourceCode, String[] actionCodes, GrantedAuthority role);

	/**
	 * @param resourceCode
	 * @param role
	 */
	void revokePermission(String resourceCode, GrantedAuthority role);

}
