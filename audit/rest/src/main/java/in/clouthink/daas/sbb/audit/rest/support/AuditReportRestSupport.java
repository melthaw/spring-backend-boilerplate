package in.clouthink.daas.sbb.audit.rest.support;

import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 * @author dz
 */
public interface AuditReportRestSupport {

	Page<AuditEventAggregation> listAuditReportByMonth(String realm, PageQueryParameter queryParameter);

	Page<AuditEventAggregation> listAuditReportByDay(String realm, PageQueryParameter queryParameter);

}
