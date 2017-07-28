package in.clouthink.daas.sbb.setting;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest"})
@Import({SettingServiceModuleConfiguration.class})
public class SettingRestApiModuleConfiguration {

}
