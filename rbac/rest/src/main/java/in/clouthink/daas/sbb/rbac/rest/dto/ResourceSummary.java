package in.clouthink.daas.sbb.rbac.rest.dto;

import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 */
public class ResourceSummary {

	static void convert(Resource resource, ResourceSummary target, Predicate<Resource> predicate) {
		BeanUtils.copyProperties(resource, target);
	}

	public static ResourceSummary from(Resource resource) {
		//always convert by default
		return from(resource, res -> true);
	}

	public static ResourceSummary from(Resource resource, Predicate<Resource> predicate) {
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

	private boolean granted = false;

	private boolean virtual;

	private String code;

	private String name;

	private String type;

	private List<ActionSummary> actions = new ArrayList<>();

	private Map<String,Object> metadata = new HashMap<>();

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

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

	public List<ActionSummary> getActions() {
		return actions;
	}

	public void setActions(List<ActionSummary> actions) {
		this.actions = actions;
	}

	public Map<String,Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String,Object> metadata) {
		this.metadata = metadata;
	}
}
