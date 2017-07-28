package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.dashboard.rest.support.ArchivedSysUserRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ArchivedUserRestSupportImpl implements ArchivedSysUserRestSupport {

	@Autowired
	private SysUserAccountService accountService;

	@Override
	public Page<SysUserSummary> listArchivedUsers(SysUserQueryParameter queryRequest) {
		queryRequest.setEnabled(null);
		Page<SysUser> userPage = accountService.listArchivedUsers(queryRequest);
		return new PageImpl<>(userPage.getContent().stream().map(SysUserSummary::from).collect(Collectors.toList()),
							  new PageRequest(queryRequest.getStart(), queryRequest.getLimit()),
							  userPage.getTotalElements());
	}

	@Override
	public SysUserDetail getArchivedUser(String id) {
		SysUser user = accountService.findById(id);
		return SysUserDetail.from(user);
	}

}
