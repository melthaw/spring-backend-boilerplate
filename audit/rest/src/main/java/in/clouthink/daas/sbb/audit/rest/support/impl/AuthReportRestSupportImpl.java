package in.clouthink.daas.sbb.audit.rest.support.impl;

import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.audit.repository.AuthEventAggregationRepository;
import in.clouthink.daas.sbb.audit.rest.support.AuthReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author dz
 */
@Component
public class AuthReportRestSupportImpl implements AuthReportRestSupport {

	@Autowired
	private AuthEventAggregationRepository authEventAggregationRepository;

	@Override
	public Page<AuthEventAggregation> listAuthReportByMonth(String realm, PageQueryParameter queryRequest) {
		return authEventAggregationRepository.findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(realm,
																										 AggregationType.MONTH,
																										 new PageRequest(
																												 queryRequest
																														 .getStart(),
																												 queryRequest
																														 .getLimit()));
	}

	@Override
	public Page<AuthEventAggregation> listAuthReportByDay(String realm, PageQueryParameter queryRequest) {
		return authEventAggregationRepository.findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(realm,
																										 AggregationType.DAY,
																										 new PageRequest(
																												 queryRequest
																														 .getStart(),
																												 queryRequest
																														 .getLimit()));
	}

}
