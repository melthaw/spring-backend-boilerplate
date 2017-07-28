package in.clouthink.daas.sbb.notice.domain.model;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 通知类别（用户创建通知的时候可以手工输入通知类别,输入后记录到历史里面,方便以后用户从历史记录中选择）
 */
@Document(collection = "NoticeCategories")
public class NoticeCategory extends StringIdentifier {

	private String value;

	private SysUser createdBy;

	private Date createdAt;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SysUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(SysUser createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
