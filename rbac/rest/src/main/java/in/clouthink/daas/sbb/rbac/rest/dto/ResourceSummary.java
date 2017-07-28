package in.clouthink.daas.sbb.rbac.rest.dto;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 */
public class ResourceSummary {

	public static ResourceSummary from(ResourceWithChildren resource) {
		//always convert by default
		return from(resource, (Resource r) -> true);
	}

	public static ResourceSummary from(ResourceWithChildren resource, Predicate<Resource> predicate) {
		if (resource == null) {
			return null;
		}
		if (!predicate.test(resource)) {
			return null;
		}
		ResourceSummary result = new ResourceSummary();
		convert(resource, result, predicate);
		return result;
	}

	private static void convert(ResourceWithChildren resource, ResourceSummary target, Predicate<Resource> predicate) {
		target.setVirtual(resource.isVirtual());
		target.setCode(resource.getCode());
		target.setName(resource.getName());
		target.setType(resource.getType());
		if (resource.hasChildren()) {
			target.setChildren(resource.getChildren()
									   .stream()
									   .filter(child -> predicate.test(child))
									   .map(resourceChild -> {
										   ResourceSummary child = new ResourceSummary();
										   convert(resourceChild, child, predicate);
										   return child;
									   })
									   .collect(Collectors.toList()));
		}
	}

	private boolean virtual;

	private String code;

	private String name;

	private String type;

	private List<ResourceSummary> children;

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ResourceSummary> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceSummary> children) {
		this.children = children;
	}
}
