package in.clouthink.daas.sbb.audit.repository.custom.impl;

import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuthEventQueryRequest;
import in.clouthink.daas.sbb.audit.repository.custom.AuthEventRepositoryCustom;
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

/**
 */
@Repository
public class AuthEventRepositoryImpl extends AbstractCustomRepositoryImpl implements AuthEventRepositoryCustom {

	@Override
	public Page<AuthEvent> queryPage(AuthEventQueryRequest queryRequest) {

		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "loginAt"));
		query.with(pageable);
		long count = mongoTemplate.count(query, AuthEvent.class);
		List<AuthEvent> list = mongoTemplate.find(query, AuthEvent.class);

		return new PageImpl<>(list, pageable, count);
	}

	private Query buildQuery(AuthEventQueryRequest request) {
		Query query = new Query();

		if (!StringUtils.isEmpty(request.getRealm())) {
			query.addCriteria(Criteria.where("realm").is(request.getRealm()));
		}

		if (!StringUtils.isEmpty(request.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(request.getUsername()));
		}

		if (request.getBeginDate() != null && request.getEndDate() != null) {
			query.addCriteria(new Criteria().andOperator(Criteria.where("loginAt").gte(request.getBeginDate()),
														 Criteria.where("loginAt").lte(request.getEndDate())));
		}
		else if (request.getBeginDate() != null) {
			query.addCriteria(Criteria.where("loginAt").gte(request.getBeginDate()));
		}
		else if (request.getEndDate() != null) {
			query.addCriteria(Criteria.where("loginAt").lte(request.getEndDate()));
		}

		Boolean succeed = request.getSucceed();
		if (succeed != null) {
			query.addCriteria(Criteria.where("succeed").is(succeed.booleanValue()));
		}

		return query;
	}

}
