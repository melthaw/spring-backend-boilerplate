package in.clouthink.daas.sbb.menu.rest.dto;

import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class MenuSummary {

	private static void convert(Resource resource, MenuSummary target) {
		BeanUtils.copyProperties(resource, target);
	}

	public static MenuSummary from(Resource resource) {
		MenuSummary result = new MenuSummary();
		convert(resource, result);
		return result;
	}

	private boolean virtual;

	private String code;

	private String name;

	private String type;

	private Map<String,Object> metadata = new HashMap<>();

	private List<MenuSummary> children = new ArrayList<>();

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

	public Map<String,Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String,Object> metadata) {
		this.metadata = metadata;
	}

	public List<MenuSummary> getChildren() {
		return children;
	}

	public void setChildren(List<MenuSummary> children) {
		this.children = children;
	}
}
