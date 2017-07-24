package in.clouthink.daas.sbb.message;

import in.clouthink.daas.sbb.message.spiImpl.MessageReceiverResolverImpl;
import in.clouthink.daas.bm.CoreModuleConfiguration;
import in.clouthink.daas.bm.core.BusinessMessageManager;
import in.clouthink.daas.bm.core.BusinessMessageRequestHandler;
import in.clouthink.daas.bm.core.MessageReceiverResolver;
import in.clouthink.daas.bm.core.impl.CompositeBusinessMessageRequestHandler;
import in.clouthink.daas.bm.support.mongodb.MongoModuleConfiguration;
import in.clouthink.daas.bm.support.push.PushModuleConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 */
@Configuration
@ComponentScan({"in.clouthink.daas.sbb.message"})
@Import({CoreModuleConfiguration.class, MongoModuleConfiguration.class, PushModuleConfiguration.class})
public class MessageModuleConfiguration implements InitializingBean {

	@Autowired
	@Qualifier("businessMessageRequestMongodbHandler")
	private BusinessMessageRequestHandler businessMessageRequestMongodbHandler;

	@Autowired
	@Qualifier("businessMessageRequestJPushHandler")
	private BusinessMessageRequestHandler businessMessageRequestJPushHandler;

	@Autowired
	private BusinessMessageManager businessMessageManager;

	@Bean
	public MessageReceiverResolver messageReceiverResolver() {
		return new MessageReceiverResolverImpl();
	}

	@Bean
	public BusinessMessageRequestHandler compositeBusinessMessageRequestHandler() {
		CompositeBusinessMessageRequestHandler result = new CompositeBusinessMessageRequestHandler();
		result.addHandler(businessMessageRequestMongodbHandler);
		result.addHandler(businessMessageRequestJPushHandler);
		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		businessMessageManager.register(compositeBusinessMessageRequestHandler());
	}

}
