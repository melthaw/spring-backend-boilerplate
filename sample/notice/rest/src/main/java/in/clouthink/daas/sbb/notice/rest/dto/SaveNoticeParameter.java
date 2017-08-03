package in.clouthink.daas.sbb.notice.rest.dto;

import in.clouthink.daas.sbb.notice.domain.request.SaveNoticeRequest;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class SaveNoticeParameter implements SaveNoticeRequest {

	private String title;

	private String category;

	private String summary;

	private String content;

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
