package in.clouthink.daas.sbb.setting;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:setting",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:setting",
						  name = "系统设置",
						  order = 2020,
						  patterns = {"/api/settings/system**", "/api/settings/system/**"},
						  actions = {@Action(code = "retrieve", name = "查看"), @Action(code = "update", name = "修改")},
						  metadata = {@Metadata(key = "state", value = "dashboard.systemSetting.list")})})
public class SystemSettingMenuConfiguration {
}
