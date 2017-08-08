package in.clouthink.daas.sbb.audit.rest.support.mock;

import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.audit.rest.support.AuditReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * AuditReportRestSupport mocker
 *
 * @author dz
 */
@Component
public class AuditReportRestSupportMockImpl implements AuditReportRestSupport {
	@Override
	public Page<AuditEventAggregation> listAuditReportByMonth(String realm, PageQueryParameter queryParameter) {
		return null;
	}

	@Override
	public Page<AuditEventAggregation> listAuditReportByDay(String realm, PageQueryParameter queryParameter) {
		return null;
	}
}
