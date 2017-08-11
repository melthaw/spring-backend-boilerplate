package in.clouthink.daas.sbb.audit;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:audit",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:auditEvent",
						  name = "系统操作审计",
						  order = 2008,
						  patterns = {"/api/auditEvents**", "/api/auditEvents/**"},
						  actions = {@Action(code = "retrieve", name = "查看"), @Action(code = "delete", name = "删除")},
						  metadata = {@Metadata(key = "state", value = "dashboard.auditEvent.list")}),

					@Menu(code = "menu:dashboard:authEvent",
						  name = "系统登录审计",
						  order = 2009,
						  patterns = {"/api/authEvents**", "/api/authEvents/**"},
						  actions = {@Action(code = "retrieve", name = "查看"), @Action(code = "delete", name = "删除")},
						  metadata = {@Metadata(key = "state", value = "dashboard.authEvent.list")})

			})
public class AuditMenuConfiguration {
}
