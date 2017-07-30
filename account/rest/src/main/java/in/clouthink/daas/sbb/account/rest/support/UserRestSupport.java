package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.*;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface UserRestSupport {

	Page<UserSummary> listUsers(UserQueryParameter queryRequest);

	UserDetail getUserDetail(String id);

	User createUser(SaveUserParameter request);

	void updateUser(String id, SaveUserParameter request);

	void deleteUser(String id, User byWho);

	void changePassword(String id, ChangePasswordRequest request);

	void enableUser(String id);

	void disableUser(String id);

	void lockUser(String id);

	void unlockUser(String id);

}
