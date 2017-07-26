package in.clouthink.daas.sbb.rbac.spi;

import in.clouthink.daas.sbb.rbac.model.Resource;

import java.util.List;

/**
 * The resource extension point
 *
 * @author dz
 */
public interface ResourceProvider {

	List<Resource> listResources();

}
