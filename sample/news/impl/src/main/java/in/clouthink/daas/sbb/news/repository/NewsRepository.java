package in.clouthink.daas.sbb.news.repository;

import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.repository.custom.NewsRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 */
public interface NewsRepository extends AbstractRepository<News>, NewsRepositoryCustom {

	Page<News> findByPublished(boolean published, Pageable pageable);

}