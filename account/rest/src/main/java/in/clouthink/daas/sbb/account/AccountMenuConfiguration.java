package in.clouthink.daas.sbb.account;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:account",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:sysuser",
						  name = "系统用户",
						  order = 2002,
						  patterns = {"/api/sysusers**", "/api/sysusers/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "create", name = "新增"),
									 @Action(code = "update", name = "修改"),
									 @Action(code = "delete", name = "删除"),
									 @Action(code = "password", name = "修改密码")},
						  metadata = {@Metadata(key = "state", value = "dashboard.sysuser.list")}),

					@Menu(code = "menu:dashboard:archiveduser",
						  name = "归档用户",
						  order = 2003,
						  patterns = {"/api/archivedusers**", "/api/archivedusers/**"},
						  actions = {@Action(code = "retrieve", name = "查看")},
						  metadata = {@Metadata(key = "state", value = "dashboard.archiveduser.list")}),

					@Menu(code = "menu:dashboard:sysrole",
						  name = "内置角色管理",
						  order = 2004,
						  patterns = {"/api/roles/sysroles**", "/api/roles/sysroles/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "binduser", name = "绑定用户"),
									 @Action(code = "unbinduser", name = "取消绑定用户")},
						  metadata = {@Metadata(key = "state", value = "dashboard.sysrole.list")}),

					@Menu(code = "menu:dashboard:extrole",
						  name = "扩展角色管理",
						  order = 2005,
						  patterns = {"/api/roles/extroles**", "/api/roles/extroles/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "create", name = "新增"),
									 @Action(code = "update", name = "修改"),
									 @Action(code = "delete", name = "删除"),
									 @Action(code = "binduser", name = "绑定用户"),
									 @Action(code = "unbinduser", name = "取消绑定用户")},
						  metadata = {@Metadata(key = "state", value = "dashboard.extrole.list")})

			})
public class AccountMenuConfiguration {

}
