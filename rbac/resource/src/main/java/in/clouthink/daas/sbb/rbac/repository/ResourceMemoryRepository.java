package in.clouthink.daas.sbb.rbac.repository;

import in.clouthink.daas.sbb.rbac.exception.ResourceException;
import in.clouthink.daas.sbb.rbac.model.ParentAware;
import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dz
 */
public class ResourceMemoryRepository implements ResourceRepository {

	private boolean overrideEnabled = false;

	//The root resources (code)
	private List<String> rootResourceCodes = new ArrayList<>();

	// code => resource
	private Map<String,Resource> resourceRepository = new HashMap<>();

	// child => parent
	private Map<String,List<String>> parentChildrenMap = new HashMap<>();

	private String hashcode = UUID.randomUUID().toString();

	@Override
	public String getHashcode() {
		return hashcode;
	}

	void updateHashcode() {
		this.hashcode = UUID.randomUUID().toString();
	}

	@Override
	public void enableOverride() {
		this.overrideEnabled = true;
	}

	@Override
	public void disableOverride() {
		this.overrideEnabled = false;
	}

	@Override
	public void addResource(Resource resource) {
		String code = resource.getCode();
		Resource prevResource = resourceRepository.get(code);
		if (prevResource != null) {
			if (!overrideEnabled) {
				throw new ResourceException(String.format(
						"The resource[code=%s] existed. And override is not allowed. Please invoke #enableOverride and try again.",
						code));
			}
		}
		else {
			rootResourceCodes.add(code);
		}

		resourceRepository.put(resource.getCode().trim(), resource);
		updateHashcode();
	}

	@Override
	public void addChildren(String parentCode, Resource... children) {
		if (StringUtils.isEmpty(parentCode)) {
			throw new ResourceException("The parent code should be not null or empty");
		}

		if (!resourceRepository.containsKey(parentCode)) {
			throw new ResourceException("The parent is not found in the repository, please add it first.");
		}

		Stream.of(children).peek(child -> {
			//do validate
			Optional.ofNullable(resourceRepository.get(child.getCode())).ifPresent(prevResource -> {
				if (!overrideEnabled) {
					throw new ResourceException(String.format(
							"The resource[code=%s] existed. And override is not allowed. Please invoke #enableOverride and try again.",
							child.getCode()));
				}
			});
		}).forEach(child -> {
			resourceRepository.put(child.getCode(), child);
			addChildren(parentCode, child.getCode());
		});

		updateHashcode();
	}

	private void addChildren(String parentCode, String childCode) {
		List<String> childrenCodes = parentChildrenMap.get(parentCode);
		if (childrenCodes == null) {
			childrenCodes = new ArrayList<>();
			parentChildrenMap.put(parentCode, childrenCodes);
		}

		childrenCodes.add(childCode);

		updateHashcode();
	}

	@Override
	public Resource findByCode(String code) {
		return resourceRepository.get(code);
	}

	@Override
	public List<Resource> getRootResources() {
		return rootResourceCodes.stream().map(code -> resourceRepository.get(code)).collect(Collectors.toList());
	}

	@Override
	public List<Resource> getFlattenResources() {
		List<String> resourceCodes = new ArrayList<>();
		resourceCodes.addAll(rootResourceCodes);
		parentChildrenMap.values().stream().forEach(codes -> resourceCodes.addAll(codes));

		return resourceCodes.stream().map(code -> resourceRepository.get(code)).collect(Collectors.toList());
	}

	@Override
	public Resource getResourceParent(String resourceCode) {
		return Optional.ofNullable(resourceCode)
					   .map(code -> resourceRepository.get(code))
					   .filter(resource -> resource instanceof ParentAware)
					   .map(resource -> resourceRepository.get(((ParentAware) resource).getParentCode()))
					   .orElse(null);
	}

	@Override
	public List<Resource> getResourceChildren(String parentCode) {
		return Optional.ofNullable(parentCode)
					   .map(code -> parentChildrenMap.get(code))
					   .map(list -> list.stream()
										.map(code -> resourceRepository.get(code))
										.collect(Collectors.toList()))
					   .orElse(Collections.emptyList());
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher resourceMatcher) {
		return getFirstMatchedResource(resourceMatcher, true);
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher resourceMatcher, boolean skipVirtual) {
		return doMatchFirstResource(resourceMatcher, this.rootResourceCodes, skipVirtual);
	}

	private Resource doMatchFirstResource(ResourceMatcher resourceMatcher,
										  List<String> resouceCodes,
										  boolean skipVirtual) {
		for (String code : resouceCodes) {
			Resource resource = resourceRepository.get(code);
			if (resourceMatcher.matched(resource)) {
				if (skipVirtual && !resource.isVirtual() || !skipVirtual) {
					return resource;
				}
			}

			List<String> childrenCodes = parentChildrenMap.get(code);
			if (childrenCodes != null) {
				Resource matchedChild = doMatchFirstResource(resourceMatcher, childrenCodes, skipVirtual);
				if (matchedChild != null) {
					return matchedChild;
				}
			}
		}

		return null;
	}

	//	@Override
	//	public List<Resource> getMatchedResources(ResourceMatcher resourceMatcher) {
	//		return getMatchedResources(resourceMatcher, true);
	//	}
	//
	//	@Override
	//	public List<Resource> getMatchedResources(ResourceMatcher resourceMatcher, boolean skipVirtual) {
	//		return this.resourceList.stream().filter(resource -> {
	//			boolean matched = resourceMatcher.matched(resource);
	//			if (!matched) {
	//				return false;
	//			}
	//			if (skipVirtual) {
	//				if (!resource.isVirtual()) {
	//					return true;
	//				}
	//			}
	//			else {
	//				return true;
	//			}
	//
	//			return false;
	//		}).collect(Collectors.toList());
	//	}

}
