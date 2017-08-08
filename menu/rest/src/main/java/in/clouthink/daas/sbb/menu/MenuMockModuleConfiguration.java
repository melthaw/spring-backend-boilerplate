package in.clouthink.daas.sbb.menu;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.menu.rest.controller", "in.clouthink.daas.sbb.menu.rest.support.mock"})
public class MenuMockModuleConfiguration {

}
