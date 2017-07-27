package in.clouthink.daas.sbb.audit.rest.support;

import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 * @author dz
 */
public interface AuthReportRestSupport {

	Page<AuthEventAggregation> listAuthReportByMonth(String realm, PageQueryParameter queryRequest);

	Page<AuthEventAggregation> listAuthReportByDay(String realm, PageQueryParameter queryRequest);

}
