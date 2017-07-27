package in.clouthink.daas.sbb.audit.rest.support.impl;

import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.audit.repository.AuditEventAggregationRepository;
import in.clouthink.daas.sbb.audit.rest.support.AuditReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author dz
 */
@Component
public class AuditReportRestSupportImpl implements AuditReportRestSupport {

	@Autowired
	private AuditEventAggregationRepository auditEventAggregationRepository;

	@Override
	public Page<AuditEventAggregation> listAuditReportByMonth(String realm, PageQueryParameter queryRequest) {
		return auditEventAggregationRepository.findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(realm,
																										  AggregationType.MONTH,
																										  new PageRequest(
																												  queryRequest
																														  .getStart(),
																												  queryRequest
																														  .getLimit()));
	}

	@Override
	public Page<AuditEventAggregation> listAuditReportByDay(String realm, PageQueryParameter queryRequest) {
		return auditEventAggregationRepository.findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(realm,
																										  AggregationType.DAY,
																										  new PageRequest(
																												  queryRequest
																														  .getStart(),
																												  queryRequest
																														  .getLimit()));
	}

}
