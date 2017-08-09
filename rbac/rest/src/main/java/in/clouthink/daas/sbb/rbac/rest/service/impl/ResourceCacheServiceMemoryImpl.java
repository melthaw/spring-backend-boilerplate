package in.clouthink.daas.sbb.rbac.rest.service.impl;

import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.service.ResourceCacheService;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author dz
 */
@Service
public class ResourceCacheServiceMemoryImpl implements ResourceCacheService {

	public static final Object LOCK_OBJECT = new Object();

	@Autowired
	private ResourceService resourceService;

	private String cacheHash = UUID.randomUUID().toString();

	private List<ResourceWithChildren> cachedValue;

	@Override
	public List<ResourceWithChildren> listResources() {
		//try get cache
		if (!isDirty()) {
			return cachedValue;
		}

		synchronized (LOCK_OBJECT) {
			//try again
			if (!isDirty()) {
				return cachedValue;
			}

			//else build new one
			List<ResourceWithChildren> result = resourceService.getRootResources()
															   .stream()
															   .filter(resource -> !resource.isOpen())
															   .map(resource -> ResourceWithChildren.from(resource))
															   .collect(Collectors.toList());

			processChildren(result);

			//cache it
			cachedValue = result;
			cacheHash = resourceService.getHashcode();

			return result;
		}
	}

	@Override
	public List<ResourceWithChildren> listResources(boolean cached) {
		if (cached) {
			return listResources();
		}

		List<ResourceWithChildren> result = resourceService.getRootResources()
														   .stream()
														   .filter(resource -> !resource.isOpen())
														   .map(resource -> ResourceWithChildren.from(resource))
														   .collect(Collectors.toList());

		processChildren(result);

		return result;
	}

	private boolean isDirty() {
		return !resourceService.getHashcode().equals(cacheHash);
	}

	private void processChildren(List<ResourceWithChildren> result) {
		result.stream().forEach(resource -> {
			List<ResourceWithChildren> children = resourceService.getResourceChildren(resource.getCode())
																 .stream()
																 .filter(child -> !child.isOpen())
																 .map(child -> ResourceWithChildren.from(child))
																 .collect(Collectors.toList());
			resource.getChildren().addAll(children);
			processChildren(children);
		});
	}

}
