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
	private List<Resource> resourceList;

	//The code and resource map
	private Map<String,Resource> resourceRepository = new HashMap<>();

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

		Resource reloaded = resourceRepository.get(parent.getCode());

		if (reloaded instanceof ResourceWithChildren) {
			ResourceWithChildren resourceWithChildren = (ResourceWithChildren) reloaded;
			if (resourceWithChildren.hasChildren()) {
				return Collections.unmodifiableList(resourceWithChildren.getChildren());
			}
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

	private void iterate(List<? extends Resource> resourceList, Map<String,Resource> resourceMap) {
		if (resourceList == null) {
			return;
		}
		resourceList.stream().forEach(resource -> {
			resourceMap.put(resource.getCode(), resource);
			if (resource instanceof ResourceWithChildren) {
				if (((ResourceWithChildren) resource).hasChildren()) {
					iterate(((ResourceWithChildren) resource).getChildren(), resourceMap);
				}
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		resourceList = resourceProviderList.stream()
										   .flatMap(provider -> provider.listResources().stream())
										   .collect(Collectors.toList());
		iterate(resourceList, resourceRepository);
	}

}
