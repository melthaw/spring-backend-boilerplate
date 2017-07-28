package in.clouthink.daas.sbb.news.repository;

import in.clouthink.daas.sbb.news.domain.model.NewsCategory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import in.clouthink.daas.security.token.core.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 */
public interface NewsCategoryRepository extends AbstractRepository<NewsCategory> {

	Page<NewsCategory> findByCreatedBy(User createdBy, Pageable pageable);

}