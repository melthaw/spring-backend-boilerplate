package in.clouthink.daas.sbb.audit.rest.support.mock;

import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.audit.rest.support.AuthReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * AuthReportRestSupport mocker
 *
 * @author dz
 */
@Component
public class AuthReportRestSupportMockImpl implements AuthReportRestSupport {
	@Override
	public Page<AuthEventAggregation> listAuthReportByMonth(String realm, PageQueryParameter queryRequest) {
		return null;
	}

	@Override
	public Page<AuthEventAggregation> listAuthReportByDay(String realm, PageQueryParameter queryRequest) {
		return null;
	}
}
