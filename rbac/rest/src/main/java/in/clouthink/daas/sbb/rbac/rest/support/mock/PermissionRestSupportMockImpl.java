package in.clouthink.daas.sbb.rbac.rest.support.mock;

import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.dto.TypedRoleSummary;
import in.clouthink.daas.sbb.rbac.rest.support.PermissionRestSupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dz
 */
@Component
public class PermissionRestSupportMockImpl implements PermissionRestSupport {

	@Override
	public List<ResourceWithChildren> listGrantedResources(String roleCode) {
		return null;
	}

	@Override
	public List<TypedRoleSummary> listGrantedRoles(String code) {
		return null;
	}

	@Override
	public void grantResourcesToRole(String roleCode, GrantResourceParameter grantRequest) {

	}

	@Override
	public void revokeResourcesFromRole(String roleCode, String resourceCode) {

	}
}
