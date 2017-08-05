package in.clouthink.daas.sbb.rbac.spi;

import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.Role;
import in.clouthink.daas.sbb.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dz
 */
public abstract class AbstractActionPermissionProvider implements ActionPermissionProvider {

	@Autowired
	private PermissionService permissionService;

	@Override
	public Action[] getGrantedPermission(String resourceCode, String userId) {
		return new Action[0];
	}

	@Override
	public Action[] getGrantedPermission(String resourceCode, String bizId, String userId) {
		return new Action[0];
	}

	public abstract List<Role> resolveRoles(String userId);

}
