package in.clouthink.daas.sbb.news.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NewsRestSupport {

	Page<NewsSummary> listNews(NewsQueryParameter queryRequest);

	NewsDetail getNewsDetail(String id);

	String createNews(SaveNewsParameter request, SysUser user);

	void updateNews(String id, SaveNewsParameter request, SysUser user);

	void deleteNews(String id, SysUser user);

	void publishNews(String id, SysUser user);

	void unpublishNews(String id, SysUser user);

	Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter);

	void deleteAttachment(String id, String fileId, SysUser user);
}
