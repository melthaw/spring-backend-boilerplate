package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.dashboard.rest.support.AuthReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import in.clouthink.daas.audit.annotation.Ignored;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dz
 */
@Ignored
@RestController
@RequestMapping("/api")
public class AppAuthReportController {

	@Autowired
	private AuthReportRestSupport authReportRestSupport;

	@ApiOperation(value = "审计报表列表（月）,支持分页")
	@RequestMapping(value = "/appAuthReports/byMonth", method = RequestMethod.GET)
	public Page<AuthEventAggregation> listAuthReportByMonth(PageQueryParameter queryRequest) {
		return authReportRestSupport.listAuthReportByMonth("frontend", queryRequest);
	}

	@ApiOperation(value = "审计报表列表（日）,支持分页")
	@RequestMapping(value = "/appAuthReports/byDay", method = RequestMethod.GET)
	public Page<AuthEventAggregation> listAuthReportByDay(PageQueryParameter queryRequest) {
		return authReportRestSupport.listAuthReportByDay("frontend", queryRequest);
	}

}
