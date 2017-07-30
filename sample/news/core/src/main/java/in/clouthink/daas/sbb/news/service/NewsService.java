package in.clouthink.daas.sbb.news.service;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.model.NewsReadHistory;
import in.clouthink.daas.sbb.news.domain.request.NewsQueryRequest;
import in.clouthink.daas.sbb.news.domain.request.SaveNewsRequest;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NewsService {

	Page<News> listNews(NewsQueryRequest queryParameter);

	News findNewsById(String id);

	void markNewsAsRead(News news, User user);

	boolean isNewsReadBySysUser(News news, User user);

	int countNewsReadHistory(News news);

	News createNews(SaveNewsRequest news, User user);

	void updateNews(String id, SaveNewsRequest news, User user);

	void deleteNews(String id, User user);

	void publishNews(String id, User user);

	void unpublishNews(String id, User user);

	Page<NewsReadHistory> listReadHistory(String id, PageQueryRequest queryRequest);

	String getImageFileObjectId(News news);

	public void deleteAttachment(String id, String fileId, User user);

}
