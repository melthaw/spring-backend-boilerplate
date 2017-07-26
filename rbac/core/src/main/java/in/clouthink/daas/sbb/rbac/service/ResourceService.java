package in.clouthink.daas.sbb.rbac.service;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;

import java.util.List;

/**
 * The resource service
 */
public interface ResourceService {

	/**
	 * find the resource by specified code
	 *
	 * @param code
	 * @return
	 */
	Resource findByCode(String code);

	/**
	 * list the root resources
	 *
	 * @return
	 */
	List<? extends Resource> getRootResources();

	/**
	 * list the children of specified resource
	 *
	 * @param parent
	 * @return
	 */
	List<? extends Resource> getResourceChildren(Resource parent);

	/**
	 * return the first matched resource and skip virtual resource by default
	 *
	 * @param matcher
	 * @return
	 * @see #getFirstMatchedResource(ResourceMatcher, boolean)
	 */
	Resource getFirstMatchedResource(ResourceMatcher matcher);

	/**
	 * return the first matched resource
	 *
	 * @param matcher
	 * @param skipVirtual
	 * @return
	 */
	Resource getFirstMatchedResource(ResourceMatcher matcher, boolean skipVirtual);

	/**
	 * return all the matched resource ( exclude the virtual resource )
	 *
	 * @param matcher
	 * @return
	 * @see #getMatchedResources(ResourceMatcher, boolean)
	 */
	List<? extends Resource> getMatchedResources(ResourceMatcher matcher);

	/**
	 * return all the matched resource
	 *
	 * @param matcher
	 * @param skipVirtual the virtual resource won't be returned if the value is true
	 * @return
	 */
	List<? extends Resource> getMatchedResources(ResourceMatcher matcher, boolean skipVirtual);

}
