package in.clouthink.daas.sbb.support.webhook.client.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * BearyChat WebHook Message Format.
 *
 * @author dz
 */
public class BearyChatMessage {

    public static BearyChatMessage from(String message) {
        BearyChatMessage result = new BearyChatMessage();
        result.setText(message);
        return result;
    }

    private String text;

    private boolean markdown = true;

    private List<BearyChatMessageAttachment> attachments = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMarkdown() {
        return markdown;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }

    public List<BearyChatMessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<BearyChatMessageAttachment> attachments) {
        this.attachments = attachments;
    }
}
