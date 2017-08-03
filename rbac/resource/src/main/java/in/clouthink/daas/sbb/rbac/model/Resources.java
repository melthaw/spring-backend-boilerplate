package in.clouthink.daas.sbb.rbac.model;

import org.springframework.beans.BeanUtils;

/**
 * @author dz
 */
public interface Resources {

	static ResourceWithChildren convert(Resource resource) {
		if (resource instanceof ResourceWithChildren) {
			return (ResourceWithChildren) resource;
		}

		DefaultResourceWithChildren result = new DefaultResourceWithChildren();
		BeanUtils.copyProperties(resource, result, "children");
		return result;
	}
}
