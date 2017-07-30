package in.clouthink.daas.sbb.attachment.domain.model;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 附件
 */
@Document(collection = "Attachments")
public class Attachment extends StringIdentifier {

	@Indexed
	private String title;

	@Indexed
	private String category;

	private String summary;

	//附件对应到存储中的id
	private String fileObjectId;

	@Indexed
	@DBRef
	private User createdBy;

	@Indexed
	private Date createdAt;

	@DBRef
	private User modifiedBy;

	@Indexed
	private Date modifiedAt;

	@Indexed
	private boolean published = false;

	private int downloadCounter;

	@Indexed
	@DBRef
	private User publishedBy;

	@Indexed
	private Date publishedAt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public int getDownloadCounter() {
		return downloadCounter;
	}

	public void setDownloadCounter(int downloadCounter) {
		this.downloadCounter = downloadCounter;
	}

	public User getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(User publishedBy) {
		this.publishedBy = publishedBy;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}
}
