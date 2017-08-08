package in.clouthink.daas.sbb.setting;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest.controller", "in.clouthink.daas.sbb.setting.rest.support.impl"})
@Import({SettingServiceModuleConfiguration.class, SettingMenuConfiguration.class})
public class SettingRestModuleConfiguration {

}
