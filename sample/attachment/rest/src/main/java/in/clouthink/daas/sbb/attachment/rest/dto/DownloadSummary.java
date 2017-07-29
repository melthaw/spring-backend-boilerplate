package in.clouthink.daas.sbb.attachment.rest.dto;

import in.clouthink.daas.sbb.attachment.domain.model.AttachmentDownloadHistory;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 */
@ApiModel
public class DownloadSummary {

	public static DownloadSummary from(AttachmentDownloadHistory newsReadHistory) {
		DownloadSummary result = new DownloadSummary();
		result.setId(newsReadHistory.getId());
		if (newsReadHistory.getDownloadedBy() != null) {
			result.setDownloadById(newsReadHistory.getDownloadedBy().getId());
			result.setDownloadByName(newsReadHistory.getDownloadedBy().getUsername());
		}
		result.setDownloadAt(newsReadHistory.getDownloadedAt());
		result.setDownloadReadAt(newsReadHistory.getLatestDownloadedAt());
		return result;
	}

	private String id;

	private String downloadById;

	private String downloadByName;

	private Date downloadAt;

	private Date downloadReadAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDownloadById() {
		return downloadById;
	}

	public void setDownloadById(String downloadById) {
		this.downloadById = downloadById;
	}

	public String getDownloadByName() {
		return downloadByName;
	}

	public void setDownloadByName(String downloadByName) {
		this.downloadByName = downloadByName;
	}

	public Date getDownloadAt() {
		return downloadAt;
	}

	public void setDownloadAt(Date downloadAt) {
		this.downloadAt = downloadAt;
	}

	public Date getDownloadReadAt() {
		return downloadReadAt;
	}

	public void setDownloadReadAt(Date downloadReadAt) {
		this.downloadReadAt = downloadReadAt;
	}
}
