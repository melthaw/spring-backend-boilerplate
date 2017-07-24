package in.clouthink.daas.sbb.shared.repository.custom;

import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 */
public class RepositoryCustomHelper {

	public static PageRequest processPagePart(Query query, PageQueryRequest queryRequest, Sort sort) {
		if (queryRequest instanceof DateRangedQueryRequest) {
			DateRangedQueryRequest dateRangedQueryRequest = (DateRangedQueryRequest) queryRequest;
			if (dateRangedQueryRequest.getBeginDate() != null && dateRangedQueryRequest.getEndDate() != null) {
				query.addCriteria(new Criteria().andOperator(Criteria.where("createdAt")
																	 .gte(dateRangedQueryRequest.getBeginDate()),
															 Criteria.where("createdAt")
																	 .lte(dateRangedQueryRequest.getEndDate())));
			}
			else if (dateRangedQueryRequest.getBeginDate() != null) {
				query.addCriteria(Criteria.where("createdAt").gte(dateRangedQueryRequest.getBeginDate()));
			}
			else if (dateRangedQueryRequest.getEndDate() != null) {
				query.addCriteria(Criteria.where("createdAt").lte(dateRangedQueryRequest.getEndDate()));
			}
		}

		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();

		PageRequest pageable = new PageRequest(start, limit, sort);
		query.with(pageable);

		return pageable;
	}

}
