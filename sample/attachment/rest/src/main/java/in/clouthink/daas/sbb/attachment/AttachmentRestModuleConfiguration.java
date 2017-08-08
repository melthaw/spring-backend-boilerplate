package in.clouthink.daas.sbb.attachment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.attachment.rest.controller",
				"in.clouthink.daas.sbb.attachment.rest.support.impl"})
@Import({AttachmentServiceModuleConfiguration.class, AttachmentMenuConfiguration.class})
public class AttachmentRestModuleConfiguration {

}
