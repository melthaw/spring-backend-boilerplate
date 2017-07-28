package in.clouthink.daas.sbb.notice.domain.request;

/**
 *
 */
public interface SaveNoticeRequest {

	String getCategory();

	String getTitle();

	//不超过140个字
	String getSummary();

	String getContent();

}
