package in.clouthink.daas.sbb.audit.rest.controller;

import in.clouthink.daas.audit.annotation.Ignored;
import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.audit.rest.support.AuditReportRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dz
 */
@Api("系统操作审计报表")
@Ignored
@RestController
@RequestMapping("/api")
public class AuditReportController {

	@Autowired
	private AuditReportRestSupport auditReportRestSupport;

	@ApiOperation(value = "审计报表列表（月）,支持分页")
	@RequestMapping(value = "/auditReports/byMonth", method = RequestMethod.GET)
	public Page<AuditEventAggregation> listAuditReportByMonth(PageQueryParameter queryRequest) {
		return auditReportRestSupport.listAuditReportByMonth("backend", queryRequest);
	}

	@ApiOperation(value = "审计报表列表（日）,支持分页")
	@RequestMapping(value = "/auditReports/byDay", method = RequestMethod.GET)
	public Page<AuditEventAggregation> listAuditReportByDay(PageQueryParameter queryRequest) {
		return auditReportRestSupport.listAuditReportByDay("backend", queryRequest);
	}

}
