package in.clouthink.daas.sbb.menu.plugin;

import in.clouthink.daas.sbb.menu.core.Menu;
import in.clouthink.daas.sbb.menu.core.MenuPlugin;
import in.clouthink.daas.sbb.menu.core.Menus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dz
 */
@Component
public class SystemMenuPlugin implements MenuPlugin {

	@Override
	public String getPluginId() {
		return "plugin:menu:system";
	}

	@Override
	public String getExtensionPointId() {
		return Menus.ROOT_EXTENSION_POINT_ID;
	}

	@Override
	public List<Menu> getMenu() {
		return Menus.load(SystemMenuPlugin.class.getResourceAsStream("menu.json"));
	}

}
