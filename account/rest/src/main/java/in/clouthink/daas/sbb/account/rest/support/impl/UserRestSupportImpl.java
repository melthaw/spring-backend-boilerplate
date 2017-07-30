package in.clouthink.daas.sbb.account.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.service.AccountService;
import in.clouthink.daas.sbb.account.rest.support.UserRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRestSupportImpl implements UserRestSupport {

	@Autowired
	private AccountService accountService;

	@Override
	public Page<UserSummary> listUsers(UserQueryParameter queryRequest) {
		Page<User> userPage = accountService.listUsers(queryRequest);
		return new PageImpl<>(userPage.getContent().stream().map(UserSummary::from).collect(Collectors.toList()),
							  new PageRequest(queryRequest.getStart(), queryRequest.getLimit()),
							  userPage.getTotalElements());
	}

	@Override
	public UserDetail getUserDetail(String id) {
		User user = accountService.findById(id);
		return UserDetail.from(user);
	}

	@Override
	public User createUser(SaveUserParameter request) {
		return accountService.createAccount(request, SysRole.ROLE_USER);
	}

	@Override
	public void updateUser(String id, SaveUserParameter request) {
		accountService.updateAccount(id, request);
	}

	@Override
	public void deleteUser(String id, User byWho) {
		accountService.archiveAccount(id, byWho);
	}

	@Override
	public void changePassword(String id, ChangePasswordRequest request) {
		accountService.changePassword(id, request.getNewPassword());
	}

	@Override
	public void enableUser(String id) {
		accountService.enable(id);
	}

	@Override
	public void disableUser(String id) {
		accountService.disable(id);
	}

	@Override
	public void lockUser(String id) {
		accountService.lock(id);
	}

	@Override
	public void unlockUser(String id) {
		accountService.unlock(id);
	}

}
