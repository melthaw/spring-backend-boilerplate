package in.clouthink.daas.sbb.rbac.support.loader;

import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * The resource loader abstraction (load from file and input stream is supported)
 *
 * @author dz
 */
public interface ResourceLoader {

	/**
	 * load from input stream
	 *
	 * @param inputStream
	 * @return
	 */
	List<ResourceWithChildren> load(InputStream inputStream);

	/**
	 * load from file
	 *
	 * @param file
	 * @return
	 */
	List<ResourceWithChildren> load(File file);

}
