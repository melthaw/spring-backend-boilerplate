package in.clouthink.daas.sbb.support.webhook.client.impl;

/**
 * BearyChat WebHook Style Customization.
 *
 * @author dz
 */
public class BearyChatMessageAttachment {

    public static BearyChatMessageAttachment info(String message) {
        BearyChatMessageAttachment result = new BearyChatMessageAttachment();
        result.setTitle("info");
        result.setText(message);
        result.setColor("#228B22");
        return result;
    }

    public static BearyChatMessageAttachment warning(String message) {
        BearyChatMessageAttachment result = new BearyChatMessageAttachment();
        result.setTitle("warning");
        result.setText(message);
        result.setColor("#FF8000");
        return result;
    }

    public static BearyChatMessageAttachment error(String message) {
        BearyChatMessageAttachment result = new BearyChatMessageAttachment();
        result.setTitle("error");
        result.setText(message);
        result.setColor("#B0171F");
        return result;
    }

    private String title;

    private String text;

    private String color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
