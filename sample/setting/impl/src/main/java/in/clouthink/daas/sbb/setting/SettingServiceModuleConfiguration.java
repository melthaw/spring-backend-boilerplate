package in.clouthink.daas.sbb.setting;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.setting.service"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.setting.repository"})
public class SettingServiceModuleConfiguration {

}
