package in.clouthink.daas.sbb.notice;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:notice",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:notice",
						  name = "通知管理",
						  order = 1002,
						  patterns = {"/api/notices**", "/api/notices/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "create", name = "新增"),
									 @Action(code = "update", name = "修改"),
									 @Action(code = "delete", name = "删除"),
									 @Action(code = "publish", name = "发布"),
									 @Action(code = "unpublish", name = "取消发布")},
						  metadata = {@Metadata(key = "state", value = "dashboard.notice.list")})})
public class NoticeMenuConfiguration {
}
