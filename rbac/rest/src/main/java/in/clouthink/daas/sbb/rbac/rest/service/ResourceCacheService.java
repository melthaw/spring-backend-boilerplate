package in.clouthink.daas.sbb.rbac.rest.service;

import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;

import java.util.List;

/**
 * @author dz
 */
public interface ResourceCacheService {

	List<ResourceWithChildren> listResources();

	List<ResourceWithChildren> listResources(boolean cached);

}
