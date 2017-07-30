package in.clouthink.daas.sbb.news.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.model.NewsReadHistory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 */
public interface NewsReadHistoryRepository extends AbstractRepository<NewsReadHistory> {

	Page<NewsReadHistory> findByNews(News news, Pageable pageable);

	NewsReadHistory findByNewsAndReadBy(News news, User user);

	int countByNews(News news);

}