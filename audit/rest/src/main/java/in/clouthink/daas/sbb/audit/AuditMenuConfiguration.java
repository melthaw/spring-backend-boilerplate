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
			menu = {@Menu(code = "menu:dashboard:sysAuditEvent",
						  name = "系统操作审计",
						  order = 2008,
						  patterns = {"/api/sysAuditEvents**", "/api/sysAuditEvents/**"},
						  actions = {@Action(code = "retrieve", name = "查看"), @Action(code = "delete", name = "删除")},
						  metadata = {@Metadata(key = "state", value = "dashboard.sysAuditEvent.list")}),

					@Menu(code = "menu:dashboard:sysAuthEvent",
						  name = "系统登录审计",
						  order = 2009,
						  patterns = {"/api/sysAuthEvents**", "/api/sysAuthEvents/**"},
						  actions = {@Action(code = "retrieve", name = "查看"), @Action(code = "delete", name = "删除")},
						  metadata = {@Metadata(key = "state", value = "dashboard.sysAuthEvent.list")})

			})
public class AuditMenuConfiguration {
}
