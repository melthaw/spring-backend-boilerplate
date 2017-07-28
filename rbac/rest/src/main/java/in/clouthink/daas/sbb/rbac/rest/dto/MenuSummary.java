package in.clouthink.daas.sbb.rbac.rest.dto;

import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
public class MenuSummary extends ResourceSummary {

	private static void convert(ResourceWithChildren resource, MenuSummary target) {
		target.setVirtual(resource.isVirtual());
		target.setCode(resource.getCode());
		target.setName(resource.getName());
		target.setType(resource.getType());
		target.setMetadata(resource.getMetadata());
		if (resource.hasChildren()) {
			target.setChildren(resource.getChildren().stream().map(resourceChild -> {
				MenuSummary child = new MenuSummary();
				convert(resourceChild, child);
				return child;
			}).collect(Collectors.toList()));
		}
	}

	public static MenuSummary from(ResourceWithChildren resource) {
		MenuSummary result = new MenuSummary();
		convert(resource, result);
		return result;
	}

	private Map<String,Object> metadata = new HashMap<>();

	public Map<String,Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String,Object> metadata) {
		this.metadata = metadata;
	}

}
