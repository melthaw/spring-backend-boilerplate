package in.clouthink.daas.sbb.news;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.news.rest.controller","in.clouthink.daas.sbb.news.rest.support.mock"})
public class NewsMockModuleConfiguration {

}
