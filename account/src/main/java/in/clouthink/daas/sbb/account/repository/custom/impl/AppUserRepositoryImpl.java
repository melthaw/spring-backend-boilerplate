package in.clouthink.daas.sbb.account.repository.custom.impl;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.AppUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.UsernameQueryRequest;
import in.clouthink.daas.sbb.account.repository.custom.AppUserRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.custom.RepositoryCustomHelper;
import in.clouthink.daas.sbb.shared.repository.custom.impl.AbstractCustomRepositoryImpl;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class AppUserRepositoryImpl extends AbstractCustomRepositoryImpl implements AppUserRepositoryCustom {

	@Override
	public Page<AppUser> queryPage(UsernameQueryRequest queryRequest) {
		Query query = new Query();
		if (!StringUtils.isEmpty(queryRequest.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(queryRequest.getUsername()));
		}

		PageRequest pageable = RepositoryCustomHelper.processPagePart(query,
																	  queryRequest,
																	  new Sort(Sort.Direction.ASC, "username"));

		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<AppUser> queryPage(AppRole role, AppUserQueryRequest queryRequest) {
		Query query = buildQuery(queryRequest);
		query.addCriteria(Criteria.where("roles").in(role));

		PageRequest pageable = RepositoryCustomHelper.processPagePart(query,
																	  queryRequest,
																	  new Sort(Sort.Direction.ASC, "username"));


		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<AppUser> queryPage(AppUserQueryRequest queryRequest) {
		Query query = buildQuery(queryRequest);
		PageRequest pageable = RepositoryCustomHelper.processPagePart(query,
																	  queryRequest,
																	  new Sort(Sort.Direction.ASC, "username"));

		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<AppUser> queryPageByAndTag(String[] tags, Pageable pageable) {
		Query query = new Query();
		query.addCriteria(Criteria.where("enabled").is(true));

		if (tags != null && tags.length > 0) {
			query.addCriteria(Criteria.where("tags").all(Arrays.asList(tags)));
		}

		query.with(pageable);

		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<AppUser> queryPageByOrTag(String[] tags, Pageable pageable) {
		Query query = new Query();
		query.addCriteria(Criteria.where("enabled").is(true));

		if (tags != null && tags.length > 0) {
			query.addCriteria(Criteria.where("tags").in(tags));
		}

		query.with(pageable);

		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<AppUser> queryPageByEnabled(Pageable pageable) {
		Query query = new Query();
		query.addCriteria(Criteria.where("enabled").is(true));
		query.with(pageable);

		long count = mongoTemplate.count(query, AppUser.class);
		List<AppUser> list = mongoTemplate.find(query, AppUser.class);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public void updateHideUserTypeAttribute(String userId, boolean hideUserType) {
		mongoTemplate.updateFirst(query(where("id").is(userId)),
								  Update.update("hideUserType", hideUserType),
								  AppUser.class);
	}

	private Query buildQuery(AppUserQueryRequest request) {
		Query query = new Query();

		if (!StringUtils.isEmpty(request.getUsername())) {
			query.addCriteria(Criteria.where("username").regex(request.getUsername()));
		}

		if (!StringUtils.isEmpty(request.getCellphone())) {
			query.addCriteria(Criteria.where("cellphone").regex(request.getCellphone()));
		}

		if (!StringUtils.isEmpty(request.getDisplayName())) {
			query.addCriteria(Criteria.where("displayName").regex(request.getDisplayName()));
		}

		if (!StringUtils.isEmpty(request.getEmail())) {
			query.addCriteria(Criteria.where("email").regex(request.getEmail()));
		}

		if (request.getGender() != null) {
			query.addCriteria(Criteria.where("gender").is(request.getGender()));
		}

		if (request.getEnabled() != null) {
			query.addCriteria(Criteria.where("enabled").is(request.getEnabled().booleanValue()));
		}

		return query;
	}

}
