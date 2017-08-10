package in.clouthink.daas.sbb.sms.aliyun.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import in.clouthink.daas.edm.sms.AdvancedSmsMessage;
import in.clouthink.daas.edm.sms.SmsMessage;
import in.clouthink.daas.edm.sms.SmsSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 */
public class SmsSenderAliyunImpl implements SmsSender {

	private static final Log logger = LogFactory.getLog(SmsSenderAliyunImpl.class);

	private AdvancedAliyunOptions options;

	private IAcsClient client;

	public SmsSenderAliyunImpl(AdvancedAliyunOptions options) {
		this.options = options;
		try {
			IClientProfile profile = DefaultProfile.getProfile(options.getArea(),
															   options.getAccessKey(),
															   options.getAccessSecret());
			DefaultProfile.addEndpoint(options.getArea(), options.getArea(), "Sms", options.getSmsEndpoint());
			client = new DefaultAcsClient(profile);
		}
		catch (Throwable e) {
			throw new SmsException("Initialize the aliyun sms client failed.", e);
		}
	}

	@Override
	public void send(SmsMessage smsMessage) {
		doSend(smsMessage, options);
	}

	@Override
	public <T> void send(AdvancedSmsMessage<T> smsMsg) {
		if (smsMsg.getOptions() == null) {
			doSend(new SmsMessage(smsMsg.getCellphone(), smsMsg.getMessage()), options);
			return;
		}

		doSend(new SmsMessage(smsMsg.getCellphone(), smsMsg.getMessage()), (AdvancedAliyunOptions) smsMsg.getOptions());
	}

	private void doSend(SmsMessage smsMessage, AdvancedAliyunOptions options) {
		SingleSendSmsRequest request = new SingleSendSmsRequest();
		try {
			request.setSignName(options.getSignature());
			request.setTemplateCode(options.getTemplateId());
			request.setParamString(smsMessage.getMessage());
			request.setRecNum(smsMessage.getCellphone());
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
		}
		catch (ServerException e) {
			logger.error("Send sms message failed", e);
			throw new SmsException(e);
		}
		catch (ClientException e) {
			logger.error("Send sms message failed", e);
			throw new SmsException(e);
		}
	}


}
