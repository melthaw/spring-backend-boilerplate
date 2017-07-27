package in.clouthink.daas.sbb.storage;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.fss.rest", "in.clouthink.daas.fss.mongodb.model"})
@EnableMongoRepositories({"in.clouthink.daas.fss.mongodb.repository"})
public class StorageRestModuleConfiguration {

}
