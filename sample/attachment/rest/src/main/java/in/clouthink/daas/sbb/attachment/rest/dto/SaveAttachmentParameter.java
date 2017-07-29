package in.clouthink.daas.sbb.attachment.rest.dto;

import in.clouthink.daas.sbb.attachment.domain.request.SaveAttachmentRequest;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class SaveAttachmentParameter implements SaveAttachmentRequest {

	private String title;

	private String category;

	private String summary;

	private String fileObjectId;

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
	public String getFileObjectId() {
		return fileObjectId;
	}

	public void setFileObjectId(String fileObjectId) {
		this.fileObjectId = fileObjectId;
	}
}
