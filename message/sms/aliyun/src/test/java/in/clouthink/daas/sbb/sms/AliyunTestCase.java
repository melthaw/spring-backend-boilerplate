package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.sbb.sms.aliyun.impl.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.sms.aliyun.impl.SmsSenderAliyunImpl;
import in.clouthink.daas.edm.sms.AdvancedSmsMessage;
import org.junit.Test;

/**
 */
public class AliyunTestCase {

	@Test
	public void testSendSms() throws Exception {
		AdvancedAliyunOptions options = new AdvancedAliyunOptions();
		options.setArea("cn-shenzhen");
		options.setAccessKey("LTAIjgtFYFzCSVXV");
		options.setAccessSecret("aHkZZuP9vqC54LFAUrlMRlUTdmyXNi");
		options.setSignature("开心帮帮兔");
		options.setTemplateId("SMS_33585577");
		options.setSmsEndpoint("sms.aliyuncs.com");

		SmsSenderAliyunImpl smsSender = new SmsSenderAliyunImpl(options);

		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone("13060078155");
		smsMessage.setOptions(options);
		smsMessage.setMessage(String.format("{\"passcode\":\"%s\"}", "234567"));

		smsSender.send(smsMessage);
	}

}
