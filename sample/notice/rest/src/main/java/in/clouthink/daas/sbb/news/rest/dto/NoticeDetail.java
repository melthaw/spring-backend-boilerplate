package in.clouthink.daas.sbb.news.rest.dto;

import in.clouthink.daas.sbb.notice.domain.model.Notice;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class NoticeDetail extends NoticeSummary {

	public static NoticeDetail from(Notice notice) {
		if (notice == null) {
			return null;
		}
		NoticeDetail result = new NoticeDetail();
		convert(notice, result);
		result.setContent(notice.getContent());
		return result;
	}

	public static NoticeDetail from(Notice notice, int readCounter) {
		if (notice == null) {
			return null;
		}
		NoticeDetail result = NoticeDetail.from(notice);
		result.setReadCounter(readCounter);
		return result;
	}

	private String content;

	private String publishedById;

	private String publishedByName;

	public String getPublishedById() {
		return publishedById;
	}

	public void setPublishedById(String publishedById) {
		this.publishedById = publishedById;
	}

	public String getPublishedByName() {
		return publishedByName;
	}

	public void setPublishedByName(String publishedByName) {
		this.publishedByName = publishedByName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
