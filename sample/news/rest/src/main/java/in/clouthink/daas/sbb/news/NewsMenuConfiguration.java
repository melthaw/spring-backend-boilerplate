package in.clouthink.daas.sbb.news;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:news",
			extensionPointId = "extension:menu:sample",
			menu = {@Menu(code = "menu:dashboard:news",
						  name = "新闻管理",
						  order = 1001,
						  patterns = {"/api/news**", "/api/news/**"},
						  actions = {@Action(code = "retrieve", name = "查看"),
									 @Action(code = "create", name = "新增"),
									 @Action(code = "update", name = "修改"),
									 @Action(code = "delete", name = "删除"),
									 @Action(code = "publish", name = "发布"),
									 @Action(code = "unpublish", name = "取消发布")},
						  metadata = {@Metadata(key = "state", value = "dashboard.news.list")})})
public class NewsMenuConfiguration {
}
