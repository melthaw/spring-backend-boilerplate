package in.clouthink.daas.sbb.news.rest.dto;

import in.clouthink.daas.sbb.news.domain.model.NewsReadHistory;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 */
@ApiModel
public class ReadSummary {

	public static ReadSummary from(NewsReadHistory newsReadHistory) {
		ReadSummary result = new ReadSummary();
		result.setId(newsReadHistory.getId());
		if (newsReadHistory.getReadBy() != null) {
			result.setReadById(newsReadHistory.getReadBy().getId());
			result.setReadByName(newsReadHistory.getReadBy().getUsername());
		}
		result.setReadAt(newsReadHistory.getReadAt());
		result.setLatestReadAt(newsReadHistory.getLatestReadAt());
		return result;
	}

	private String id;

	private String readById;

	private String readByName;

	private Date readAt;

	private Date latestReadAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReadById() {
		return readById;
	}

	public void setReadById(String readById) {
		this.readById = readById;
	}

	public String getReadByName() {
		return readByName;
	}

	public void setReadByName(String readByName) {
		this.readByName = readByName;
	}

	public Date getReadAt() {
		return readAt;
	}

	public void setReadAt(Date readAt) {
		this.readAt = readAt;
	}

	public Date getLatestReadAt() {
		return latestReadAt;
	}

	public void setLatestReadAt(Date latestReadAt) {
		this.latestReadAt = latestReadAt;
	}
}
