package in.clouthink.daas.sbb.rbac.rest.support;


import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;

import java.util.List;

/**
 */
public interface PermissionRestSupport {

	/**
	 * @param roleCode
	 * @return
	 */
	List<PrivilegedResourceWithChildren> listGrantedResources(String roleCode);

	/**
	 * @param code
	 * @return
	 */
	List<TypedRole> listGrantedRoles(String code);

	/**
	 * @param roleCode
	 * @param grantRequest
	 */
	void grantResourcesToRole(String roleCode, GrantResourceParameter grantRequest);

	/**
	 * @param roleCode
	 * @param resourceCode
	 */
	void revokeResourcesFromRole(String roleCode, String resourceCode);
}
