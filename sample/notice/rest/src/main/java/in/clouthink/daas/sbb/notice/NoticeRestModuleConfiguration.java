package in.clouthink.daas.sbb.notice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.notice.rest.controller", "in.clouthink.daas.sbb.notice.rest.support.impl"})
@Import({NoticeServiceModuleConfiguration.class, NoticeMenuConfiguration.class})
public class NoticeRestModuleConfiguration {

}
