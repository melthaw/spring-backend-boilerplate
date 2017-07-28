package in.clouthink.daas.sbb.news.rest.dto;

import in.clouthink.daas.sbb.news.domain.model.News;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 */
@ApiModel
public class NewsSummary {

	static void convert(News news, NewsSummary result) {
		BeanUtils.copyProperties(news, result, "createdBy", "modifiedBy", "publishedBy");
		if (news.getCreatedBy() != null) {
			result.setCreatedById(news.getCreatedBy().getId());
			result.setCreatedByName(news.getCreatedBy().getUsername());
		}
		if (news.getPublishedBy() != null) {
			result.setPublishedById(news.getPublishedBy().getId());
			result.setPublishedByName(news.getPublishedBy().getUsername());
		}

		if (news.getModifiedBy() != null) {
			result.setModifiedByName(news.getModifiedBy().getUsername());
		}
	}

	public static NewsSummary from(News news) {
		if (news == null) {
			return null;
		}
		NewsSummary result = new NewsSummary();
		convert(news, result);
		return result;
	}

	public static NewsSummary from(News news, boolean read) {
		if (news == null) {
			return null;
		}
		NewsSummary result = from(news);
		result.setRead(read);
		return result;
	}

	private String id;

	private String category;

	private String title;

	private String summary;

	private boolean published;

	private boolean read;

	private int readCounter;

	private String createdById;

	private String createdByName;

	private String publishedById;

	private String publishedByName;

	private String modifiedByName;

	private Date modifiedAt;

	private Date publishedAt;

	private Date createdAt;

	private News.NewsType newsType;

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

	public News.NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(News.NewsType newsType) {
		this.newsType = newsType;
	}
}
