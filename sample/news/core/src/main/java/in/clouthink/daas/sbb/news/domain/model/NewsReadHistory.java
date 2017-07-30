package in.clouthink.daas.sbb.news.domain.model;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 */
@Document(collection = "NewsReadHistories")
public class NewsReadHistory extends StringIdentifier {

	@Indexed
	@DBRef(lazy = true)
	private News news;

	@Indexed
	@DBRef(lazy = true)
	private User readBy;

	private Date readAt;

	private Date latestReadAt;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public User getReadBy() {
		return readBy;
	}

	public void setReadBy(User readBy) {
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
