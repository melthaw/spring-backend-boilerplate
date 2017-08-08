package in.clouthink.daas.sbb.menu;

import in.clouthink.daas.sbb.menu.core.MenuPlugin;
import in.clouthink.daas.sbb.menu.core.MenuResourceProvider;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MenuModuleConfiguration {

	@Bean
	@Autowired(required = false)
	public ResourceProvider menuResourceProvider(List<MenuPlugin> menuPluginList) {
		return new MenuResourceProvider(menuPluginList);
	}

}
