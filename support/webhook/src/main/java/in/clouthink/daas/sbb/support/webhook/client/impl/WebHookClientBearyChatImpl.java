package in.clouthink.daas.sbb.support.webhook.client.impl;

import in.clouthink.daas.sbb.support.webhook.client.WebHookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Please feel free to add your webhook impl ( for example: slack )
 *
 * @author dz
 */
@Component
public class WebHookClientBearyChatImpl implements WebHookClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendMessage(String url, String message) {
        BearyChatMessage bearyChatMessage = BearyChatMessage.from(message);
        restTemplate.postForEntity(url, bearyChatMessage, Map.class);
    }

    @Override
    public void sendMessage(String url, String message, String body) {
        BearyChatMessage bearyChatMessage = BearyChatMessage.from(message);
        bearyChatMessage.getAttachments().add(BearyChatMessageAttachment.error(body));
        restTemplate.postForEntity(url, bearyChatMessage, Map.class);
    }

}
