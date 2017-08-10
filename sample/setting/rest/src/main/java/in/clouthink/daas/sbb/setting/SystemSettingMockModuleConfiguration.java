package in.clouthink.daas.sbb.setting;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.rest.controller", "in.clouthink.daas.sbb.setting.rest.support.mock"})
public class SystemSettingMockModuleConfiguration {

}
