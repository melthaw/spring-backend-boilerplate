package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.support.request.AuthEventQueryParameter;
import in.clouthink.daas.sbb.dashboard.rest.support.AuthEventRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * frontend登录日志
 */
@RestController
@RequestMapping("/api")
public class AppAuthRestController {

	@Autowired
	private AuthEventRestSupport authEventRestSupport;

	@ApiOperation(value = "用户登录日志列表,支持分页,支持动态查询(按名称,状态查询)")
	@RequestMapping(value = "/appAuthEvents", method = RequestMethod.GET)
	public Page<AuthEvent> listAuthEventPage(AuthEventQueryParameter queryRequest) {
		queryRequest.setRealm("frontend");
		return authEventRestSupport.listAuthEventPage(queryRequest);
	}

	@ApiOperation(value = "用户登录日志详情")
	@RequestMapping(value = "/appAuthEvents/{id}", method = RequestMethod.GET)
	public AuthEvent getAuthEventDetail(@PathVariable String id) {
		return authEventRestSupport.getAuthEventDetail(id);
	}

	@ApiOperation(value = "删除日志-以天为单位")
	@RequestMapping(value = "/appAuthEvents/byDay/{day}", method = RequestMethod.DELETE)
	public void deleteAuditEventsByDay(@PathVariable Date day) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		authEventRestSupport.deleteAuthEventsByDay("frontend", day, user);
	}

	@ApiOperation(value = "删除日志-删除指定日期（不包括）之前的所有数据")
	@RequestMapping(value = "/appAuthEvents/beforeDay/{day}", method = RequestMethod.DELETE)
	public void deleteAuditEventsBeforeDay(@PathVariable Date day) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		authEventRestSupport.deleteAuthEventsBeforeDay("frontend", day, user);
	}

}
