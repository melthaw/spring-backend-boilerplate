package in.clouthink.daas.sbb.account.rest.controller;

import in.clouthink.daas.sbb.account.rest.dto.SysUserDetail;
import in.clouthink.daas.sbb.account.rest.dto.SysUserQueryParameter;
import in.clouthink.daas.sbb.account.rest.dto.SysUserSummary;
import in.clouthink.daas.sbb.account.rest.support.ArchivedSysUserRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("已归档的用户管理")
@RestController
@RequestMapping("/api")
public class ArchivedSysUserRestController {

	@Autowired
	private ArchivedSysUserRestSupport archivedSysUserRestSupport;

	@ApiOperation(value = "查看归档用户列表,支持分页,支持动态查询（用户名等）")
	@RequestMapping(value = "/archivedusers", method = RequestMethod.GET)
	public Page<SysUserSummary> listArchivedUsers(SysUserQueryParameter queryRequest) {
		return archivedSysUserRestSupport.listArchivedUsers(queryRequest);
	}

	@ApiOperation(value = "查看归档用户基本信息")
	@RequestMapping(value = "/archivedusers/{id}", method = RequestMethod.GET)
	public SysUserDetail getArchivedUser(@PathVariable String id) {
		return archivedSysUserRestSupport.getArchivedUser(id);
	}

}
