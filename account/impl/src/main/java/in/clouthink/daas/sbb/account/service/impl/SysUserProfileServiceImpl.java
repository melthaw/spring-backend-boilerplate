package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.account.service.SysUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class SysUserProfileServiceImpl implements SysUserProfileService {

	@Autowired
	private SysUserAccountService accountService;

	@Override
	public SysUser findUserById(String userId) {
		return accountService.findById(userId);
	}

	@Override
	public SysUser changeUserProfile(String userId, ChangeUserProfileRequest request) {
		return accountService.changeUserProfile(userId, request);
	}

	@Override
	public SysUser changeUserPassword(String userId, String oldPassword, String newPassword) {
		return accountService.changePassword(userId, oldPassword, newPassword);
	}

}
