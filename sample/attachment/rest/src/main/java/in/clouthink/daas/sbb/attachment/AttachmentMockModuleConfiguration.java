package in.clouthink.daas.sbb.attachment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.attachment.rest.controller",
				"in.clouthink.daas.sbb.attachment.rest.support.mock"})
public class AttachmentMockModuleConfiguration {

}
