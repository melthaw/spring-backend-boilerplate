package in.clouthink.daas.sbb.menu;

import in.clouthink.daas.sbb.rbac.model.Action;
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

	private Menu parent;

	private List<String> patterns = new ArrayList<>();

	private List<Action> actions = new ArrayList<>();

	private Map<String,Object> metadata = new HashMap<>();

	private int sort;

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
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public MenuExtensionPoint getExtensionPoint() {
		return extensionPoint;
	}

	public void setExtensionPoint(MenuExtensionPoint extensionPoint) {
		this.extensionPoint = extensionPoint;
	}

}
