package in.clouthink.daas.sbb.audit.repository.custom.impl;

import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuditEventQueryRequest;
import in.clouthink.daas.sbb.audit.repository.custom.AuditEventRepositoryCustom;
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

@Repository
public class AuditEventRepositoryImpl extends AbstractCustomRepositoryImpl implements AuditEventRepositoryCustom {

	@Override
	public Page<AuditEvent> queryPage(AuditEventQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "requestedAt"));
		query.with(pageable);
		long count = mongoTemplate.count(query, AuditEvent.class);
		List<AuditEvent> list = mongoTemplate.find(query, AuditEvent.class);

		return new PageImpl<>(list, pageable, count);
	}

	private Query buildQuery(AuditEventQueryRequest request) {
		Query query = new Query();
		Boolean error = request.getError();

		if (!StringUtils.isEmpty(request.getRealm())) {
			query.addCriteria(Criteria.where("realm").is(request.getRealm()));
		}

		if (!StringUtils.isEmpty(request.getRequestedBy())) {
			query.addCriteria(Criteria.where("requestedBy").regex(request.getRequestedBy()));
		}

		if (request.getBeginDate() != null && request.getEndDate() != null) {
			query.addCriteria(new Criteria().andOperator(Criteria.where("requestedAt").gte(request.getBeginDate()),
														 Criteria.where("requestedAt").lte(request.getEndDate())));
		}
		else if (request.getBeginDate() != null) {
			query.addCriteria(Criteria.where("requestedAt").gte(request.getBeginDate()));
		}
		else if (request.getEndDate() != null) {
			query.addCriteria(Criteria.where("requestedAt").lte(request.getEndDate()));
		}

		if (error != null) {
			query.addCriteria(Criteria.where("error").is(error.booleanValue()));
		}
		return query;
	}

}
