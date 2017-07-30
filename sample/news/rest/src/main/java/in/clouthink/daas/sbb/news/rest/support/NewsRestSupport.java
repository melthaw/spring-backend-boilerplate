package in.clouthink.daas.sbb.news.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NewsRestSupport {

	Page<NewsSummary> listNews(NewsQueryParameter queryRequest);

	NewsDetail getNewsDetail(String id);

	String createNews(SaveNewsParameter request, User user);

	void updateNews(String id, SaveNewsParameter request, User user);

	void deleteNews(String id, User user);

	void publishNews(String id, User user);

	void unpublishNews(String id, User user);

	Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter);

	void deleteAttachment(String id, String fileId, User user);
}
