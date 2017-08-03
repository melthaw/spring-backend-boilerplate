package in.clouthink.daas.sbb.menu.core;

/**
 * @author dz
 */
public class MenuExtensionPoint {

	private String id;

	private String description;

	public MenuExtensionPoint() {
	}

	public MenuExtensionPoint(String id) {
		this.id = id;
	}

	public MenuExtensionPoint(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
