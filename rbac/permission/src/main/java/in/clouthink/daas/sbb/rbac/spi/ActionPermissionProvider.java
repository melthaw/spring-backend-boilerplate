package in.clouthink.daas.sbb.rbac.spi;

import in.clouthink.daas.sbb.rbac.model.Action;

/**
 * @author dz
 */
public interface ActionPermissionProvider {

	/**
	 * @param resourceCode
	 * @param userId
	 * @return
	 */
	Action[] getGrantedPermission(String resourceCode, String userId);

	/**
	 * @param resourceCode
	 * @param bizId
	 * @param userId
	 * @return
	 */
	Action[] getGrantedPermission(String resourceCode, String bizId, String userId);

}
