package in.clouthink.daas.sbb.menu.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dz
 */
public class DefaultMenuPlugin implements MenuPlugin {

	private String extensionPointId;

	private String pluginId;

	private List<Menu> menu = new ArrayList<>();

	@Override
	public String getExtensionPointId() {
		return extensionPointId;
	}

	public void setExtensionPointId(String extensionPointId) {
		this.extensionPointId = extensionPointId;
	}

	@Override
	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	@Override
	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
}
