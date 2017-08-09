package in.clouthink.daas.sbb.rbac.rest.support.impl;

import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.service.ResourceCacheService;
import in.clouthink.daas.sbb.rbac.rest.support.ResourceRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceRestSupportImpl implements ResourceRestSupport {

	@Autowired
	private ResourceCacheService resourceService;

	@Override
	public List<PrivilegedResourceWithChildren> listResources() {
		return resourceService.listResources();
	}

}
