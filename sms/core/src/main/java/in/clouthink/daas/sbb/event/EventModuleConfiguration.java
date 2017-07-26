package in.clouthink.daas.sbb.event;

import in.clouthink.daas.sbb.event.sms.service.SmsService;
import in.clouthink.daas.sbb.event.sms.service.impl.SmsServiceImpl;
import in.clouthink.daas.sbb.event.sms.support.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.event.sms.support.SmsSenderAliyunImpl;
import in.clouthink.daas.edm.sms.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.event"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.event"})
@EnableConfigurationProperties({AdvancedAliyunOptions.class})
public class EventModuleConfiguration {

	//	@Bean
	//	@Autowired
	//	public JavaMailSender javaMailSender(EmailOptions emailOptions) {
	//		JavaMailSenderImpl result = new JavaMailSenderImpl();
	//		result.setHost(emailOptions.getHost());
	//		result.setPort(emailOptions.getPort());
	//		result.setUsername(emailOptions.getUsername());
	//		result.setPassword(emailOptions.getPassword());
	//		result.getJavaMailProperties().put("mail.smtp.auth", true);
	//		result.getJavaMailProperties().put("mail.smtp.starttls.enable", true);
	//		return result;
	//	}
	//
	//	@Bean
	//	@Autowired
	//	public EmailSender emailSender(EmailOptions emailOptions) {
	//		return new EmailSenderImpl(javaMailSender(emailOptions));
	//	}

	@Bean
	@Autowired
	public SmsSender yunpianSmsSender(AdvancedAliyunOptions options) {
		return new SmsSenderAliyunImpl(options);
	}

	@Bean
	@Autowired
	public SmsService smsServiceImpl() {
		return new SmsServiceImpl();
	}

}
