package in.clouthink.daas.sbb.menu.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.DefaultAction;
import in.clouthink.daas.sbb.rbac.model.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dz
 */
public class Menu implements Resource, Serializable {

	private boolean virtual = false;

	private boolean open = false;

	private String code;

	private String name;

	private List<String> patterns = new ArrayList<>();

	@JsonDeserialize(contentAs = DefaultAction.class)
	private List<Action> actions = new ArrayList<>();

	private Map<String,Object> metadata = new HashMap<>();

	private int order;

	private MenuExtensionPoint extensionPoint;

	public String getType() {
		return "menu";
	}

	@Override
	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}

	@Override
	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	@Override
	public Map<String,Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String,Object> metadata) {
		this.metadata = metadata;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public MenuExtensionPoint getExtensionPoint() {
		return extensionPoint;
	}

	public void setExtensionPoint(MenuExtensionPoint extensionPoint) {
		this.extensionPoint = extensionPoint;
	}

}
