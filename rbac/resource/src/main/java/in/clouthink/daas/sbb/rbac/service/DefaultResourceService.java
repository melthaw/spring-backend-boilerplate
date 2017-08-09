package in.clouthink.daas.sbb.rbac.service;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceChild;
import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;
import in.clouthink.daas.sbb.rbac.repository.ResourceMemoryRepository;
import in.clouthink.daas.sbb.rbac.repository.ResourceRepository;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dz
 */
public class DefaultResourceService implements ResourceService, InitializingBean {

	private List<ResourceProvider> resourceProviderList = new ArrayList<>();

	private ResourceRepository resourceRepository = new ResourceMemoryRepository();

	/**
	 * The default object is ResourceMemoryRepository, replace it with your impl.
	 *
	 * @param resourceRepository
	 */
	public void setResourceRepository(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}

	public List<ResourceProvider> getResourceProviderList() {
		return resourceProviderList;
	}

	/**
	 * Please provide the resource before the resource service initialized.
	 *
	 * @param resourceProviderList
	 */
	public void setResourceProviderList(List<ResourceProvider> resourceProviderList) {
		this.resourceProviderList = resourceProviderList;
	}

	@Override
	public String getHashcode() {
		return resourceRepository.getHashcode();
	}

	@Override
	public Resource findByCode(String code) {
		return resourceRepository.findByCode(code);
	}

	@Override
	public List<Resource> getRootResources() {
		return resourceRepository.getRootResources();
	}

	@Override
	public List<Resource> getFlattenResources() {
		return resourceRepository.getFlattenResources();
	}

	@Override
	public List<Resource> getResourceChildren(String resourceCode) {
		return resourceRepository.getResourceChildren(resourceCode);
	}

	@Override
	public Resource getResourceParent(String resourceCode) {
		return resourceRepository.getResourceParent(resourceCode);
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher matcher) {
		return resourceRepository.getFirstMatchedResource(matcher);
	}

	@Override
	public Resource getFirstMatchedResource(ResourceMatcher matcher, boolean skipVirtual) {
		return resourceRepository.getFirstMatchedResource(matcher, skipVirtual);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		resourceProviderList.stream()
							.flatMap(provider -> provider.listResources().stream())
							.filter(stream -> !(stream instanceof ResourceChild))
							.forEach(resource -> resourceRepository.addResource(resource));

		resourceProviderList.stream()
							.flatMap(provider -> provider.listResources().stream())
							.filter(stream -> (stream instanceof ResourceChild))
							.map(stream -> (ResourceChild) stream)
							.forEach(resource -> resourceRepository.addChildren(resource.getParentCode(), resource));
	}

}
