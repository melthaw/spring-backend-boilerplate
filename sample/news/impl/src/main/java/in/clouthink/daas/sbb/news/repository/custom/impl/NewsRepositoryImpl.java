package in.clouthink.daas.sbb.news.repository.custom.impl;

import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.request.NewsQueryRequest;
import in.clouthink.daas.sbb.news.repository.custom.NewsRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.QueryBuilder;
import in.clouthink.daas.sbb.shared.repository.custom.impl.AbstractCustomRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
public class NewsRepositoryImpl extends AbstractCustomRepositoryImpl implements NewsRepositoryCustom {

	@Override
	public Page<News> queryPage(NewsQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "createdAt"));
		query.with(pageable);
		long count = mongoTemplate.count(query, News.class);
		List<News> list = mongoTemplate.find(query, News.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public void updateReadCounter(String id, int readCounter) {
		mongoTemplate.updateFirst(query(where("id").is(id)), update("readCounter", readCounter), News.class);
	}

	private Query buildQuery(NewsQueryRequest request) {
		QueryBuilder queryBuilder = new QueryBuilder();
		if (!StringUtils.isEmpty(request.getTitle())) {
			queryBuilder.add(Criteria.where("title").regex(request.getTitle()));
		}
		if (!StringUtils.isEmpty(request.getCategory())) {
			queryBuilder.add(Criteria.where("category").regex(request.getCategory()));
		}

		if (request.getPublished() != null) {
			queryBuilder.add(Criteria.where("published").is(request.getPublished().booleanValue()));
		}

		if (request.getNewsType() != null) {
			queryBuilder.add(Criteria.where("newsType").is(request.getNewsType()));
		}

		if (request.getCreatedAtBegin() != null && request.getCreatedAtEnd() != null) {
			queryBuilder.and(Criteria.where("createdAt").gte(request.getCreatedAtBegin()))
					    .and(Criteria.where("createdAt").lte(request.getCreatedAtEnd()));
		} else if (request.getCreatedAtBegin() != null) {
			queryBuilder.add(Criteria.where("createdAt").gte(request.getCreatedAtBegin()));
		} else if (request.getCreatedAtEnd() != null) {
			queryBuilder.add(Criteria.where("createdAt").lte(request.getCreatedAtEnd()));
		}

		if (request.getBeginDate() != null && request.getEndDate() != null) {
			queryBuilder.and(Criteria.where("publishedAt").gte(request.getBeginDate()))
						.and(Criteria.where("publishedAt").lte(request.getEndDate()));
		} else if (request.getBeginDate() != null) {
			queryBuilder.add(Criteria.where("publishedAt").gte(request.getBeginDate()));
		} else if (request.getEndDate() != null) {
			queryBuilder.add(Criteria.where("publishedAt").lte(request.getEndDate()));
		}
		return queryBuilder.build();
	}

}
