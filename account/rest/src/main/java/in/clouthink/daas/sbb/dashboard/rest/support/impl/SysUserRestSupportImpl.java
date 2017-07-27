package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.dashboard.rest.support.SysUserRestSupport;
import in.clouthink.daas.sbb.dashboard.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SysUserRestSupportImpl implements SysUserRestSupport {

	@Autowired
	private SysUserAccountService accountService;

	@Override
	public Page<SysUserSummary> listSysUsers(SysUserQueryParameter queryRequest) {
		Page<SysUser> userPage = accountService.listUsers(queryRequest);
		return new PageImpl<>(userPage.getContent().stream().map(SysUserSummary::from).collect(Collectors.toList()),
							  new PageRequest(queryRequest.getStart(), queryRequest.getLimit()),
							  userPage.getTotalElements());
	}

	@Override
	public SysUserDetail getSysUserDetail(String id) {
		SysUser user = accountService.findById(id);
		return SysUserDetail.from(user);
	}

	@Override
	public SysUser createSysUser(SaveSysUserParameter request) {
		return accountService.createAccount(request, SysRole.ROLE_USER);
	}

	@Override
	public void updateSysUser(String id, SaveSysUserParameter request) {
		accountService.updateAccount(id, request);
	}

	@Override
	public void deleteSysUser(String id, SysUser byWho) {
		accountService.archiveAccount(id, byWho);
	}

	@Override
	public void changePassword(String id, ChangePasswordRequest request) {
		accountService.changePassword(id, request.getNewPassword());
	}

	@Override
	public void enableSysUser(String id) {
		accountService.enable(id);
	}

	@Override
	public void disableSysUser(String id) {
		accountService.disable(id);
	}

	@Override
	public void lockSysUser(String id) {
		accountService.lock(id);
	}

	@Override
	public void unlockSysUser(String id) {
		accountService.unlock(id);
	}

}
