package in.clouthink.daas.sbb.support.webhook;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Add the following config to your application.properties or application.yml
 * <p>
 * <code>
 * in.clouthink.daas.sbb.support.webhook.url=
 * </code>
 */
@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.support.webhook")
public class WebHookSupportProperties {

    //please change it or override by configration
    //if the value is null or empty, the webhook feature will be disabled.
    private String url = "https://hook.bearychat.com/=bwBrq/incoming/403427090b0555b1804f0160f338ae29";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
