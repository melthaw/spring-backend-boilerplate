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
public class SampleMenuPlugin implements MenuPlugin {

	@Override
	public String getPluginId() {
		return "plugin:menu:sample";
	}

	@Override
	public String getExtensionId() {
		return Menus.ROOT_EXTENSION_POINT_ID;
	}

	@Override
	public Menu getMenu() {
		Menu result = new Menu();
		result.setVirtual(true);
		result.setOpen(true);
		result.setCode("menu:sample");
		result.setName("示例");
		result.setSort(100);
		result.setExtensionPoint(new MenuExtensionPoint("extension:menu:sample"));
		return result;
	}
}
