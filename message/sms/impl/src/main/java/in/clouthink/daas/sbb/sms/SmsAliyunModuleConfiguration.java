package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.edm.sms.SmsSender;
import in.clouthink.daas.sbb.sms.aliyun.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.sms.aliyun.SmsSenderAliyunImpl;
import in.clouthink.daas.sbb.sms.aliyun.SmsServiceImpl;
import in.clouthink.daas.sbb.sms.history.SmsHistoryModuleConfiguration;
import in.clouthink.daas.sbb.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(AdvancedAliyunOptions.class)
@Import({SmsHistoryModuleConfiguration.class})
public class SmsAliyunModuleConfiguration {

	@Bean
	@Autowired
	public SmsService smsServiceImpl() {
		return new SmsServiceImpl();
	}

	@Bean
	@Autowired
	public SmsSender smsSenderImpl(AdvancedAliyunOptions options) {
		return new SmsSenderAliyunImpl(options);
	}

}
