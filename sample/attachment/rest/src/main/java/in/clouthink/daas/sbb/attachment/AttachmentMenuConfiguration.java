package in.clouthink.daas.sbb.attachment;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:attachment",
			extensionPointId = "extension:menu:sample",
			menu = {@Menu(code = "menu:dashboard:attachment",
						  name = "下载管理",
						  order = 1003,
						  patterns = {"/api/attachments**", "/api/attachments/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "create", name = "新增"),
									 @Action(code = "update", name = "修改"),
									 @Action(code = "delete", name = "删除"),
									 @Action(code = "publish", name = "发布"),
									 @Action(code = "unpublish", name = "取消发布")},
						  metadata = {@Metadata(key = "state", value = "dashboard.attachment.list")})})
public class AttachmentMenuConfiguration {
}
