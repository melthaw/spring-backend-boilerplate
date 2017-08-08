package in.clouthink.daas.sbb.sms;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.rest.controller", "in.clouthink.daas.sbb.sms.rest.support.mock"})
public class SmsMockModuleConfiguration {

}
