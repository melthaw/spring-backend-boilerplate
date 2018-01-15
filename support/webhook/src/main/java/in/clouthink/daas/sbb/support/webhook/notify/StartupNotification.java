package in.clouthink.daas.sbb.support.webhook.notify;

import in.clouthink.daas.sbb.support.webhook.WebHookSupportProperties;
import in.clouthink.daas.sbb.support.webhook.client.WebHookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @author dz
 */
public class StartupNotification extends AbstractNotification implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private WebHookSupportProperties webhookConfigurationProperties = new WebHookSupportProperties();

    @Autowired
    private WebHookClient webHookClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (StringUtils.isEmpty(webhookConfigurationProperties.getUrl())) {
            return;
        }

        webHookClient.sendMessage(webhookConfigurationProperties.getUrl(),
                                  String.format("%s startup succeed.", getAppName()));
    }

}
