package in.clouthink.daas.sbb.notice.domain.model;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 */
@Document(collection = "NoticeReadHistories")
public class NoticeReadHistory extends StringIdentifier {

	@Indexed
	@DBRef(lazy = true)
	private Notice notice;

	@Indexed
	@DBRef(lazy = true)
	private SysUser readBy;

	private Date readAt;

	private Date latestReadAt;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public SysUser getReadBy() {
		return readBy;
	}

	public void setReadBy(SysUser readBy) {
		this.readBy = readBy;
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
