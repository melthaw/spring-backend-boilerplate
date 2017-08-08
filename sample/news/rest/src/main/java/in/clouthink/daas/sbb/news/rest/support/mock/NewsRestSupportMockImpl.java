package in.clouthink.daas.sbb.news.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.news.rest.support.NewsRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * NewsRestSupport mocker
 *
 * @author dz
 */
@Component
public class NewsRestSupportMockImpl implements NewsRestSupport {
	@Override
	public Page<NewsSummary> listNews(NewsQueryParameter queryRequest) {
		return null;
	}

	@Override
	public NewsDetail getNewsDetail(String id) {
		return null;
	}

	@Override
	public String createNews(SaveNewsParameter request, User user) {
		return null;
	}

	@Override
	public void updateNews(String id, SaveNewsParameter request, User user) {

	}

	@Override
	public void deleteNews(String id, User user) {

	}

	@Override
	public void publishNews(String id, User user) {

	}

	@Override
	public void unpublishNews(String id, User user) {

	}

	@Override
	public Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter) {
		return null;
	}

	@Override
	public void deleteAttachment(String id, String fileId, User user) {

	}
}
