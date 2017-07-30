package in.clouthink.daas.sbb.news.domain.model;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 新闻类别（用户创建新闻的时候可以手工输入新闻类别,输入后记录到历史里面,方便以后用户从历史记录中选择）
 */
@Document(collection = "NewsCategories")
public class NewsCategory extends StringIdentifier {

	private String value;

	@Indexed
	@DBRef
	private User createdBy;

	private Date createdAt;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
}
