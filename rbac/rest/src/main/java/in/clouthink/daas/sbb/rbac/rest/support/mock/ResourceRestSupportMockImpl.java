package in.clouthink.daas.sbb.rbac.rest.support.mock;

import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.support.ResourceRestSupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dz
 */
@Component
public class ResourceRestSupportMockImpl implements ResourceRestSupport {
	@Override
	public List<ResourceWithChildren> listResources() {
		return null;
	}
}
