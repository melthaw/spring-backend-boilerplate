package in.clouthink.daas.sbb.openapi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.openapi"})
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
public class OpenApiModuleConfiguration {

}
