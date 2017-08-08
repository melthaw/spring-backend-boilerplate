package in.clouthink.daas.sbb.account.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.*;
import in.clouthink.daas.sbb.account.rest.support.UserRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * UserRestSupport mocker
 *
 * @author dz
 */
@Component
public class UserRestSupportMockImpl implements UserRestSupport {
	@Override
	public Page<UserSummary> listUsers(UserQueryParameter queryRequest) {
		return null;
	}

	@Override
	public UserDetail getUserDetail(String id) {
		return null;
	}

	@Override
	public User createUser(SaveUserParameter request) {
		return null;
	}

	@Override
	public void updateUser(String id, SaveUserParameter request) {

	}

	@Override
	public void deleteUser(String id, User byWho) {

	}

	@Override
	public void changePassword(String id, ChangePasswordRequest request) {

	}

	@Override
	public void enableUser(String id) {

	}

	@Override
	public void disableUser(String id) {

	}

	@Override
	public void lockUser(String id) {

	}

	@Override
	public void unlockUser(String id) {

	}
}
