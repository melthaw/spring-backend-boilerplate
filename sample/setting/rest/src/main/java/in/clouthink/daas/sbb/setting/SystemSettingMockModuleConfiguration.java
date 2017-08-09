package in.clouthink.daas.sbb.setting;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest.controller", "in.clouthink.daas.sbb.setting.rest.support.mock"})
@EnableConfigurationProperties(SettingConfigurationProperties.class)
public class SystemSettingMockModuleConfiguration {

	@Bean
	public SettingInitializingBean SettingInitializingBean() {
		return new SettingInitializingBean();
	}

}
