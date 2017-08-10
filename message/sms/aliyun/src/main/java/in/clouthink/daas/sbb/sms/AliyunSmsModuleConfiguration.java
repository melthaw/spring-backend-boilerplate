package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.edm.sms.SmsSender;
import in.clouthink.daas.sbb.sms.aliyun.event.PasscodeMessageEventListener;
import in.clouthink.daas.sbb.sms.aliyun.impl.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.sms.aliyun.impl.SmsSenderAliyunImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AdvancedAliyunOptions.class)
public class AliyunSmsModuleConfiguration {

	@Bean
	public EventListener passcodeMessageEventListener() {
		return new PasscodeMessageEventListener();
	}

	@Bean
	@Autowired
	public SmsSender smsSenderImpl(AdvancedAliyunOptions options) {
		return new SmsSenderAliyunImpl(options);
	}

}
