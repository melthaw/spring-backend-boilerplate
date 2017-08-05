package in.clouthink.daas.sbb.rbac.rest.support;


import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;

import java.util.List;

/**
 */
public interface ResourceRestSupport {

	/**
	 * @return
	 */
	List<ResourceWithChildren> listResources();

}
