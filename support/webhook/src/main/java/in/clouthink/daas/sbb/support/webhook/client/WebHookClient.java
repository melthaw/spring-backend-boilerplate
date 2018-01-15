package in.clouthink.daas.sbb.support.webhook.client;

/**
 * @author dz
 */
public interface WebHookClient {

	/**
	 * The implementation must handle the exception
	 *
	 * @param url
	 * @param message
	 */
	void sendMessage(String url, String message);

	/**
	 * The implementation must handle the exception
	 *
	 * @param url
	 * @param message
	 */
	void sendMessage(String url, String message, String body);

}
