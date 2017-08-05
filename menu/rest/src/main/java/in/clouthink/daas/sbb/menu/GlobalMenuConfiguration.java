package in.clouthink.daas.sbb.menu;

import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.ExtensionPoint;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import in.clouthink.daas.sbb.menu.core.MenuPlugin;
import in.clouthink.daas.sbb.menu.core.Menus;
import in.clouthink.daas.sbb.menu.plugin.SystemMenuPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Show two ways to declare the menu plugin (extend the root extension point and declare new sub-menu)
 *
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:sample",
			extensionPointId = Menus.ROOT_EXTENSION_POINT_ID,
			menu = {@Menu(
					virtual = true,
					code = "menu:dashboard:sample",
					name = "模块示例",
					order = 100,
					metadata = {@Metadata(key = "icon", value = "fa fa-gear")},
					extensionPoint = {@ExtensionPoint(id = "extension:menu:sample")})})
public class GlobalMenuConfiguration {

	@Bean
	public MenuPlugin systemMenuPlugin() {
		return new SystemMenuPlugin();
	}

}
