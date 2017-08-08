package in.clouthink.daas.sbb.rbac;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:permission",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:permission:ext",
						  name = "权限管理",
						  order = 2006,
						  patterns = {"/api/permissions/ext**", "/api/permissions/ext/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "grant", name = "授权"),
									 @Action(code = "revoke", name = "取消授权")},
						  metadata = {@Metadata(key = "state", value = "dashboard.permission.ext.list")}),

					@Menu(code = "menu:dashboard:permission:sys",
						  name = "内置权限管理",
						  order = 2007,
						  patterns = {"/api/permissions/sys**", "/api/permissions/sys/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "grant", name = "授权"),
									 @Action(code = "revoke", name = "取消授权")},
						  metadata = {@Metadata(key = "state", value = "dashboard.permission.sys.list")}),

			})
public class RbacMenuConfiguration {

}
