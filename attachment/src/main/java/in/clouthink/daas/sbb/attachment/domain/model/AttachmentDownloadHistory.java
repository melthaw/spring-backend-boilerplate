package in.clouthink.daas.sbb.attachment.domain.model;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 */
@Document(collection = "AttachmentDownloadHistories")
public class AttachmentDownloadHistory extends StringIdentifier {

	@Indexed
	@DBRef(lazy = true)
	private Attachment attachment;

	@Indexed
	@DBRef(lazy = true)
	private SysUser downloadedBy;

	private Date downloadedAt;

	private Date latestDownloadedAt;

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public SysUser getDownloadedBy() {
		return downloadedBy;
	}

	public void setDownloadedBy(SysUser downloadedBy) {
		this.downloadedBy = downloadedBy;
	}

	public Date getDownloadedAt() {
		return downloadedAt;
	}

	public void setDownloadedAt(Date downloadedAt) {
		this.downloadedAt = downloadedAt;
	}

	public Date getLatestDownloadedAt() {
		return latestDownloadedAt;
	}

	public void setLatestDownloadedAt(Date latestDownloadedAt) {
		this.latestDownloadedAt = latestDownloadedAt;
	}
}
