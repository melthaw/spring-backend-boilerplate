package in.clouthink.daas.sbb.news.rest;

import in.clouthink.daas.sbb.notice.NoticeServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.notice.rest"})
@Import(NoticeServiceModuleConfiguration.class)
public class NoticeRestApiModuleConfiguration {

}
