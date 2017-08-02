package in.clouthink.daas.sbb.rbac.spi;

import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.Resource;

import java.util.List;

/**
 * The resource extension point
 *
 * @author dz
 */
public interface ResourceProvider {

	/**
	 * The resource provider's name (normally the module name)
	 *
	 * @return
	 */
	String getName();

	/**
	 * @return the resource list of the provider
	 */
	List<? extends Resource> listResources();

	/**
	 * @param resourceCode if the resourceCode is null ,empty action array returned.
	 * @param userId       if the user is disabled or locked or expired ,empty action array returned.
	 * @return the allowed action list
	 */
	Action[] decide(String resourceCode, String userId);

	/**
	 * @param resource if the resource is null ,empty action array returned.
	 * @param userId   if the user is disabled or locked or expired ,empty action array returned.
	 * @return the allowed action list
	 */
	Action[] decide(Resource resource, String userId);

}
