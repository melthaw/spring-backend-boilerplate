package in.clouthink.daas.sbb.setting;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest.controller", "in.clouthink.daas.sbb.setting.rest.support.impl"})
@Import({SettingServiceModuleConfiguration.class, SystemSettingMenuConfiguration.class})
@EnableConfigurationProperties(SettingConfigurationProperties.class)
public class SystemSettingRestModuleConfiguration {

	@Bean
	public SettingInitializingBean SettingInitializingBean() {
		return new SettingInitializingBean();
	}

}
