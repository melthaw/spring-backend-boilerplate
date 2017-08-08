package in.clouthink.daas.sbb.notice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.notice.rest.controller", "in.clouthink.daas.sbb.notice.rest.support.mock"})
public class NoticeMockModuleConfiguration {

}
