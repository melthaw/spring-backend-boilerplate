package in.clouthink.daas.sbb.account.repository.custom.impl;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.UsernameQueryRequest;
import in.clouthink.daas.sbb.account.repository.custom.SysUserRepositoryCustom;
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
public class SysUserRepositoryImpl extends AbstractCustomRepositoryImpl implements SysUserRepositoryCustom {

	@Override
	public Page<SysUser> queryPage(UsernameQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = new Query();
		if (!StringUtils.isEmpty(queryRequest.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(queryRequest.getUsername()));
		}
		query.addCriteria(Criteria.where("deleted").ne(true));

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.ASC, "rank", "username"));
		query.with(pageable);
		long count = mongoTemplate.count(query, SysUser.class);
		List<SysUser> list = mongoTemplate.find(query, SysUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<SysUser> queryPage(SysRole role, SysUserQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = new Query();
		query.addCriteria(Criteria.where("roles").in(role));
		if (!StringUtils.isEmpty(queryRequest.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(queryRequest.getUsername()));
		}
		query.addCriteria(Criteria.where("deleted").ne(true));

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "username"));
		query.with(pageable);
		long count = mongoTemplate.count(query, SysUser.class);
		List<SysUser> list = mongoTemplate.find(query, SysUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<SysUser> queryPage(SysUserQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);
		query.addCriteria(Criteria.where("deleted").ne(true));

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.ASC, "rank", "username"));
		query.with(pageable);
		long count = mongoTemplate.count(query, SysUser.class);
		List<SysUser> list = mongoTemplate.find(query, SysUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<SysUser> queryArchivedUsers(SysUserQueryRequest queryRequest) {
		int start = queryRequest.getStart();
		int limit = queryRequest.getLimit();
		Query query = buildQuery(queryRequest);
		query.addCriteria(Criteria.where("deleted").is(true));

		PageRequest pageable = new PageRequest(start, limit, new Sort(Sort.Direction.DESC, "deletedAt"));
		query.with(pageable);
		long count = mongoTemplate.count(query, SysUser.class);
		List<SysUser> list = mongoTemplate.find(query, SysUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	private Query buildQuery(SysUserQueryRequest request) {
		Query query = new Query();
		if (!StringUtils.isEmpty(request.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(request.getUsername()));
		}
		if (!StringUtils.isEmpty(request.getCellphone())) {
			query.addCriteria(Criteria.where("contactPhone").regex(request.getCellphone()));
		}
		if (!StringUtils.isEmpty(request.getEmail())) {
			query.addCriteria(Criteria.where("email").regex(request.getEmail()));
		}
		if (request.getEnabled() != null) {
			query.addCriteria(Criteria.where("enabled").is(request.getEnabled().booleanValue()));
		}

		return query;
	}

}
