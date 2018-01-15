package in.clouthink.daas.sbb.support.webhook;

import in.clouthink.daas.sbb.support.webhook.client.WebHookClient;
import in.clouthink.daas.sbb.support.webhook.client.impl.WebHookClientBearyChatImpl;
import in.clouthink.daas.sbb.support.webhook.notify.ShutdownNotification;
import in.clouthink.daas.sbb.support.webhook.notify.StartupNotification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WebHookSupportProperties.class)
public class WebHookSupportConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebHookClient webhookClient() {
        return new WebHookClientBearyChatImpl();
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> startupNotification() {
        return new StartupNotification();
    }

    @Bean
    public ApplicationListener<ApplicationFailedEvent> shutdownNotificatioin() {
        return new ShutdownNotification();
    }

}
