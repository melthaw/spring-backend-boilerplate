package in.clouthink.daas.sbb.rbac.model;

import org.springframework.beans.BeanUtils;

/**
 * @author dz
 */
public class Resources {

	public static ResourceWithChildren convert(Resource resource) {
		if (resource instanceof ResourceWithChildren) {
			return (ResourceWithChildren) resource;
		}

		DefaultResourceWithChildren result = new DefaultResourceWithChildren();
		BeanUtils.copyProperties(resource, resource, "children");
		return result;
	}
}
