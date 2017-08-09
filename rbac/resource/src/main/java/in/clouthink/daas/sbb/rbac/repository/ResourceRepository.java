package in.clouthink.daas.sbb.rbac.repository;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;

import java.util.List;

/**
 * The resource registry which manage the resource by the resource code (as key)
 */
public interface ResourceRepository {

	/**
	 * @return the hash code of the resource repository
	 */
	String getHashcode();

	/**
	 * if the resource with same code existed, add new resource will override the existed one.
	 */
	void enableOverride();

	/**
	 * if the resource with same code existed, add new resource will throw an exception.
	 */
	void disableOverride();

	/**
	 * The resource will be added to the root level ,
	 * and the resource code is case-sensitive which means
	 * the resource coded hello and HELLO will be taken as different.
	 *
	 * @param resource
	 */
	void addResource(Resource resource);

	/**
	 * The resource will append to the target as children
	 *
	 * @param parentCode
	 * @param children
	 */
	void addChildren(String parentCode, Resource... children);

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
	List<Resource> getRootResources();

	/**
	 * list all resources in flatten
	 *
	 * @return
	 */
	List<Resource> getFlattenResources();

	/**
	 * get the parent of specified resource
	 *
	 * @param resourceCode
	 * @return
	 */
	Resource getResourceParent(String resourceCode);

	/**
	 * list the children of specified resource
	 *
	 * @param parentCode
	 * @return
	 */
	List<Resource> getResourceChildren(String parentCode);

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

	//	/**
	//	 * return all the matched resource ( exclude the virtual resource )
	//	 *
	//	 * @param matcher
	//	 * @return
	//	 * @see #getMatchedResources(ResourceMatcher, boolean)
	//	 */
	//	List<Resource> getMatchedResources(ResourceMatcher matcher);
	//
	//	/**
	//	 * return all the matched resource
	//	 *
	//	 * @param matcher
	//	 * @param skipVirtual the virtual resource won't be returned if the value is true
	//	 * @return
	//	 */
	//	List<Resource> getMatchedResources(ResourceMatcher matcher, boolean skipVirtual);

}
