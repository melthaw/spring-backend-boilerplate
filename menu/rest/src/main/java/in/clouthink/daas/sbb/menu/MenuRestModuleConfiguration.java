package in.clouthink.daas.sbb.menu;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.menu.rest"})
@Import({MenuModuleConfiguration.class, GlobalMenuConfiguration.class})
public class MenuRestModuleConfiguration {


}
