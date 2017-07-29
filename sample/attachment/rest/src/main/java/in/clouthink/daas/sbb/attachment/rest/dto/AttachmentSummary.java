package in.clouthink.daas.sbb.attachment.rest.dto;

import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 *
 */
@ApiModel
public class AttachmentSummary {

	static void convert(Attachment attachment, AttachmentSummary result) {
		BeanUtils.copyProperties(attachment, result, "createdBy", "modifiedBy", "publishedBy");
		if (attachment.getCreatedBy() != null) {
			result.setCreatedById(attachment.getCreatedBy().getId());
			result.setCreatedByName(attachment.getCreatedBy().getUsername());
		}
		if (attachment.getPublishedBy() != null) {
			result.setPublishedById(attachment.getPublishedBy().getId());
			result.setPublishedByName(attachment.getPublishedBy().getUsername());
		}
	}

	public static AttachmentSummary from(Attachment attachment) {
		if (attachment == null) {
			return null;
		}
		AttachmentSummary result = new AttachmentSummary();
		convert(attachment, result);
		return result;
	}

	public static AttachmentSummary from(Attachment attachment, boolean downloaded) {
		if (attachment == null) {
			return null;
		}
		AttachmentSummary result = AttachmentSummary.from(attachment);
		result.setDownloaded(downloaded);
		return result;
	}

	private String id;

	private String category;

	private String title;

	private String summary;

	private String fileObjectId;

	private boolean published;

	private boolean downloaded = false;

	private int downloadCounter = 0;

	private String createdById;

	private String createdByName;

	private String publishedById;

	private String publishedByName;

	private Date publishedAt;

	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFileObjectId() {
		return fileObjectId;
	}

	public void setFileObjectId(String fileObjectId) {
		this.fileObjectId = fileObjectId;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public int getDownloadCounter() {
		return downloadCounter;
	}

	public void setDownloadCounter(int downloadCounter) {
		this.downloadCounter = downloadCounter;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

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

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
