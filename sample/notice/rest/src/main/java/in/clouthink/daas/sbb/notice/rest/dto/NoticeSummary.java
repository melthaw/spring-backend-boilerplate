package in.clouthink.daas.sbb.notice.rest.dto;

import in.clouthink.daas.sbb.notice.domain.model.Notice;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 *
 */
@ApiModel
public class NoticeSummary {

	static void convert(Notice notice, NoticeSummary result) {
		BeanUtils.copyProperties(notice, result, "createdBy", "modifiedBy", "publishedBy");
		if (notice.getCreatedBy() != null) {
			result.setCreatedById(notice.getCreatedBy().getId());
			result.setCreatedByName(notice.getCreatedBy().getUsername());
		}
		if (notice.getPublishedBy() != null) {
			result.setPublishedById(notice.getPublishedBy().getId());
			result.setPublishedByName(notice.getPublishedBy().getUsername());
		}

		if (notice.getModifiedBy() != null) {
			result.setModifiedByName(notice.getModifiedBy().getUsername());
		}
	}

	public static NoticeSummary from(Notice notice) {
		if (notice == null) {
			return null;
		}
		NoticeSummary result = new NoticeSummary();
		convert(notice, result);
		return result;
	}

	public static NoticeSummary from(Notice notice, boolean read) {
		if (notice == null) {
			return null;
		}
		NoticeSummary result = NoticeSummary.from(notice);
		result.setRead(read);
		return result;
	}


	private String id;

	private String title;

	private String category;

	private String summary;

	private boolean published;

	private boolean read = false;

	private int readCounter = 0;

	private String createdById;

	private String createdByName;

	private String publishedById;

	private String publishedByName;

	private Date publishedAt;

	private String modifiedByName;

	private Date modifiedAt;

	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public int getReadCounter() {
		return readCounter;
	}

	public void setReadCounter(int readCounter) {
		this.readCounter = readCounter;
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

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

}
