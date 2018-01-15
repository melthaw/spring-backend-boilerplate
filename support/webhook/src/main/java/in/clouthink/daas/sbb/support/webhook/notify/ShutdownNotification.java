package in.clouthink.daas.sbb.support.webhook.notify;

import in.clouthink.daas.sbb.support.webhook.WebHookSupportProperties;
import in.clouthink.daas.sbb.support.webhook.client.WebHookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @author dz
 */
public class ShutdownNotification extends AbstractNotification implements ApplicationListener<ApplicationFailedEvent> {

    @Autowired
    private WebHookSupportProperties webhookConfigurationProperties = new WebHookSupportProperties();

    @Autowired
    private WebHookClient webhookClient;

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        if (StringUtils.isEmpty(webhookConfigurationProperties.getUrl())) {
            return;
        }

        webhookClient.sendMessage(webhookConfigurationProperties.getUrl(),
                                  String.format("%s startup failed. The cause is %s",
                                                getAppName(),
                                                event.getException().getMessage()),
                                  event.getException().getMessage());
    }


}
