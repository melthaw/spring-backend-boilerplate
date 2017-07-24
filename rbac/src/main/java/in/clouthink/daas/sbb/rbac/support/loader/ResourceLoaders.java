package in.clouthink.daas.sbb.rbac.support.loader;

import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;

import java.util.List;

/**
 * @author dz
 */
public class ResourceLoaders {

	private static final ResourceJsonLoader resourceJsonLoader = new ResourceJsonLoader();

	public static List<ResourceWithChildren> loadJson(String jsonFile) {
		return resourceJsonLoader.load(ResourceLoaders.class.getClassLoader().getResourceAsStream(jsonFile));
	}

}
