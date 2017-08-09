package in.clouthink.daas.sbb.rbac.rest.service;

import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;

import java.util.List;

/**
 * @author dz
 */
public interface ResourceCacheService {

	List<PrivilegedResourceWithChildren> listResources();

	List<PrivilegedResourceWithChildren> listResources(boolean cached);

}
