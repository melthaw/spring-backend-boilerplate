package in.clouthink.daas.sbb.menu.plugin;

import in.clouthink.daas.sbb.menu.core.Menu;
import in.clouthink.daas.sbb.menu.core.MenuExtensionPoint;
import in.clouthink.daas.sbb.menu.core.MenuPlugin;
import in.clouthink.daas.sbb.menu.core.Menus;
import org.springframework.stereotype.Component;

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
	public Menu getMenu() {
		Menu result = new Menu();
		result.setVirtual(true);
		result.setOpen(true);
		result.setCode("menu:sample");
		result.setName("系统管理");
		result.setOrder(200);
		result.setExtensionPoint(new MenuExtensionPoint("extension:menu:system"));
		return result;
	}
}
