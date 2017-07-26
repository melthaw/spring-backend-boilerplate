package in.clouthink.daas.sbb.attachment.repository.custom.impl;

import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.request.AttachmentQueryRequest;
import in.clouthink.daas.sbb.attachment.repository.custom.AttachmentRepositoryCustom;
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
public class AttachmentRepositoryImpl extends AbstractCustomRepositoryImpl implements AttachmentRepositoryCustom {

	@Override
	public Page<Attachment> queryPage(AttachmentQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "createdAt"));
		query.with(pageable);
		long count = mongoTemplate.count(query, Attachment.class);
		List<Attachment> list = mongoTemplate.find(query, Attachment.class);

		return new PageImpl<>(list, pageable, count);
	}

	private Query buildQuery(AttachmentQueryRequest request) {
		Query query = new Query();
		if (!StringUtils.isEmpty(request.getTitle())) {
			query.addCriteria(Criteria.where("title").regex(request.getTitle()));
		}
		if (!StringUtils.isEmpty(request.getCategory())) {
			query.addCriteria(Criteria.where("category").regex(request.getCategory()));
		}

		if (request.getPublished() != null) {
			query.addCriteria(Criteria.where("published").is(request.getPublished().booleanValue()));
		}

		if (request.getCreatedAtBegin() != null && request.getCreatedAtEnd() != null) {
			query.addCriteria(new Criteria().andOperator(Criteria.where("createdAt").gte(request.getCreatedAtBegin()),
														 Criteria.where("createdAt").lte(request.getCreatedAtEnd())));
		}
		else if (request.getCreatedAtBegin() != null) {
			query.addCriteria(Criteria.where("createdAt").gte(request.getCreatedAtBegin()));
		}
		else if (request.getCreatedAtEnd() != null) {
			query.addCriteria(Criteria.where("createdAt").lte(request.getCreatedAtEnd()));
		}

		if (request.getBeginDate() != null && request.getEndDate() != null) {
			query.addCriteria(new Criteria().andOperator(Criteria.where("publishedAt").gte(request.getBeginDate()),
														 Criteria.where("publishedAt").lte(request.getEndDate())));
		}
		else if (request.getBeginDate() != null) {
			query.addCriteria(Criteria.where("publishedAt").gte(request.getBeginDate()));
		}
		else if (request.getEndDate() != null) {
			query.addCriteria(Criteria.where("publishedAt").lte(request.getEndDate()));
		}
		return query;
	}

	@Override
	public void updateDownloadCounter(String id, int downloadCounter) {
		mongoTemplate.updateFirst(query(where("id").is(id)),
								  update("downloadCounter", downloadCounter),
								  Attachment.class);
	}


}
