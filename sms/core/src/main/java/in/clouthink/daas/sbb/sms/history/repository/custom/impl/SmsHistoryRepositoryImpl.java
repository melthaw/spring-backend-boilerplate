package in.clouthink.daas.sbb.sms.history.repository.custom.impl;

import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import in.clouthink.daas.sbb.sms.history.repository.custom.SmsHistoryRepositoryCustom;
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
public class SmsHistoryRepositoryImpl extends AbstractCustomRepositoryImpl implements SmsHistoryRepositoryCustom {

	@Override
	public Page<SmsHistory> queryPage(SmsHistoryQueryRequest parameter) {
		Query query = createQuery(parameter);
		long count = mongoTemplate.count(query, SmsHistory.class);

		int start = parameter.getStart();
		int limit = parameter.getLimit();
		PageRequest pageable = new PageRequest(start, limit);
		query.with(pageable).with(new Sort(Sort.Direction.DESC, "createdAt"));
		List<SmsHistory> list = mongoTemplate.find(query, SmsHistory.class);

		return new PageImpl<SmsHistory>(list, pageable, count);
	}

	private Query createQuery(SmsHistoryQueryRequest parameter) {
		Query query = new Query();

		if (!StringUtils.isEmpty(parameter.getCellphone())) {
			query.addCriteria(Criteria.where("cellphone").regex(parameter.getCellphone()));
		}

		if (parameter.getStatus() != null) {
			query.addCriteria(Criteria.where("status").is(parameter.getStatus()));
		}

		if (!StringUtils.isEmpty(parameter.getCategory())) {
			query.addCriteria(Criteria.where("category").is(parameter.getCategory()));
		}

		if (parameter.getBeginDate() != null && parameter.getEndDate() != null) {
			Criteria criteria = new Criteria().andOperator(Criteria.where("createdAt").gte(parameter.getBeginDate()),
														   Criteria.where("createdAt").lte(parameter.getEndDate()));
			query.addCriteria(criteria);
		}
		else if (parameter.getBeginDate() != null) {
			query.addCriteria(Criteria.where("createdAt").gte(parameter.getBeginDate()));
		}
		else if (parameter.getEndDate() != null) {
			query.addCriteria(Criteria.where("createdAt").lte(parameter.getEndDate()));
		}

		return query;
	}

}
