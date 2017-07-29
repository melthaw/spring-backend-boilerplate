package in.clouthink.daas.sbb.news;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.news.service"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.news.repository"})
@EnableConfigurationProperties({NewsConfigurationProperties.class})
public class NewsServiceModuleConfiguration {

}
