package in.clouthink.daas.sbb.attachment.rest;

import in.clouthink.daas.sbb.attachment.AttachmentServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.attachment.rest"})
@Import({AttachmentServiceModuleConfiguration.class})
public class AttachmentRestModuleConfiguration {

}
