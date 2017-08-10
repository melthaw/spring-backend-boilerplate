package in.clouthink.daas.sbb.sms.history;

import in.clouthink.daas.sbb.menu.annotation.Action;
import in.clouthink.daas.sbb.menu.annotation.EnableMenu;
import in.clouthink.daas.sbb.menu.annotation.Menu;
import in.clouthink.daas.sbb.menu.annotation.Metadata;
import org.springframework.context.annotation.Configuration;


/**
 * @author dz
 */
@Configuration
@EnableMenu(pluginId = "plugin:menu:sms",
			extensionPointId = "extension:menu:system",
			menu = {@Menu(code = "menu:dashboard:sms",
						  name = "短信发送记录",
						  order = 2010,
						  patterns = {"/api/smsHistories**", "/api/smsHistories/**"},
						  actions = {@Action(code = "retrieve", name = "查看")},
						  metadata = {@Metadata(key = "state", value = "dashboard.smsHistory.list")})})
public class SmsHistoryMenuConfiguration {
}
