package in.clouthink.daas.sbb.rbac.service;


import in.clouthink.daas.sbb.rbac.model.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * The role-based(GrantedAuthority) access control service
 */
public interface PermissionService {

	/**
	 * @param resourceCode
	 * @return the resource
	 */
	Resource findResourceByCode(String resourceCode);

	/**
	 * @param resourcePattern
	 * @return the first one matched the pattern
	 */
	Resource getMatchedResource(String resourcePattern);

	/**
	 * @param resourceCode
	 * @return
	 */
	List<Role> getGrantedRoles(String resourceCode);

	/**
	 * List all the permissions
	 *
	 * @param resource
	 * @return
	 */
	List<Role> getGrantedRoles(Resource resource);

	/**
	 * @param resourceCode
	 * @param role
	 * @return the permission
	 */
	Permission getPermission(String resourceCode, GrantedAuthority role);

	/**
	 * @param role
	 * @return
	 */
	List<ResourceWithChildren> getGrantedResources(GrantedAuthority role);

	/**
	 * @param roles
	 * @return
	 */
	List<ResourceWithChildren> getGrantedResources(List<GrantedAuthority> roles);

	/**
	 * @param role
	 * @param filter
	 * @return
	 */
	List<ResourceWithChildren> getGrantedResources(GrantedAuthority role, ResourceMatcher filter);

	/**
	 * @param roles
	 * @param filter
	 * @return
	 */
	List<ResourceWithChildren> getGrantedResources(List<GrantedAuthority> roles, ResourceMatcher filter);

	/**
	 * @param resourceCode
	 * @param role
	 * @return
	 */
	boolean isGranted(String resourceCode, GrantedAuthority role);

	/**
	 * @param resource
	 * @param role
	 * @return
	 */
	boolean isGranted(Resource resource, GrantedAuthority role);

}
