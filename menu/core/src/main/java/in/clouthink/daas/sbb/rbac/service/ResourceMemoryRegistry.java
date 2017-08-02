package in.clouthink.daas.sbb.rbac.service;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;
import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dz
 */
public class ResourceMemoryRegistry implements ResourceRegistry, InitializingBean {

	@Autowired(required = false)
	private List<ResourceProvider> resourceProviderList = new ArrayList<>();

	//The root resources
	private List<ResourceWithChildren> resourceList = new ArrayList<>();

	//The code and resource map
	private Map<String,ResourceWithChildren> resourceRepository = new HashMap<>();
	//
	//	@Override
	//	public void register(String name, List<Resource> resourceList) {
	//
	//	}
	//
	//	@Override
	//	public void unregister(String name) {
	//
	//	}

	@Override
	public Resource findByCode(String code) {
		return resourceRepository.get(code);
	}

	@Override
	public List<Resource> getRootResources() {
		return Collections.unmodifiableList(resourceList);
	}

	@Override
	public List<Resource> getResourceChildren(Resource parent) {
		if (parent == null) {
			return Collections.unmodifiableList(Collections.emptyList());
		}

		ResourceWithChildren reloadedParent = resourceRepository.get(parent.getCode());
		if (reloadedParent.hasChildren()) {
			return Collections.unmodifiableList(reloadedParent.getChildren());
		}
		return Collections.unmodifiableList(Collections.emptyList());
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher resourceMatcher) {
		return getFirstMatchedResource(resourceMatcher, true);
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher resourceMatcher, boolean skipVirtual) {
		return doMatchFirstResource(resourceMatcher, this.resourceList, skipVirtual);
	}

	private Resource doMatchFirstResource(ResourceMatcher resourceMatcher,
										  List<? extends Resource> resources,
										  boolean skipVirtual) {
		for (Resource resource : resources) {
			if (resourceMatcher.matched(resource)) {
				if (skipVirtual) {
					if (!resource.isVirtual()) {
						return resource;
					}
				}
				else {
					return resource;
				}
			}

			if (resource instanceof ResourceWithChildren && (((ResourceWithChildren) resource).hasChildren())) {
				Resource matchedChild = doMatchFirstResource(resourceMatcher,
															 ((ResourceWithChildren) resource).getChildren(),
															 skipVirtual);
				if (matchedChild != null) {
					return matchedChild;
				}
			}
		}
		return null;
	}

	@Override
	public List<Resource> getMatchedResources(ResourceMatcher resourceMatcher) {
		return getMatchedResources(resourceMatcher, true);
	}

	@Override
	public List<Resource> getMatchedResources(ResourceMatcher resourceMatcher, boolean skipVirtual) {
		return this.resourceList.stream().filter(resource -> {
			boolean matched = resourceMatcher.matched(resource);
			if (!matched) {
				return false;
			}
			if (skipVirtual) {
				if (!resource.isVirtual()) {
					return true;
				}
			}
			else {
				return true;
			}

			return false;
		}).collect(Collectors.toList());
	}

	private void iterate(List<? extends ResourceWithChildren> resourceList,
						 Map<String,ResourceWithChildren> resourceMap) {
		if (resourceList == null) {
			return;
		}
		resourceList.stream().forEach(resource -> {
			resourceMap.put(resource.getCode(), resource);
			if (resource.hasChildren()) {
				iterate(resource.getChildren(), resourceMap);
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		resourceProviderList.forEach(resourceProvider -> resourceList.addAll(resourceProvider.listResources()));
		iterate(resourceList, resourceRepository);
	}

}
