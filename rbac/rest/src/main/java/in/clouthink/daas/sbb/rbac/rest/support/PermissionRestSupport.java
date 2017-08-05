package in.clouthink.daas.sbb.rbac.rest.support;


import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.ResourceSummary;
import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.dto.TypedRoleSummary;

import java.util.List;

/**
 */
public interface PermissionRestSupport {

	/**
	 * @param roleCode
	 * @return
	 */
	List<ResourceWithChildren> listGrantedResources(String roleCode);

	/**
	 * @param code
	 * @return
	 */
	List<TypedRoleSummary> listGrantedRoles(String code);

	/**
	 * @param code
	 * @param typedRoleCodes
	 */
	void grantResourcesToRoles(String code, GrantResourceParameter typedRoleCodes);

	/**
	 * @param code
	 * @param typedRoleCodeArray
	 */
	void revokeResourcesFromRoles(String code, String typedRoleCodeArray);
}
