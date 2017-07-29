package in.clouthink.daas.sbb.setting.rest;

import in.clouthink.daas.sbb.setting.SettingServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest"})
@Import({SettingServiceModuleConfiguration.class})
public class SettingRestApiModuleConfiguration {

}
