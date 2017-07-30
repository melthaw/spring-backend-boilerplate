package in.clouthink.daas.sbb.account.repository.custom;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.UserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.UsernameQueryRequest;
import org.springframework.data.domain.Page;

/**
 * 系统用户持久层扩展
 *
 * @author dz
 */
public interface UserRepositoryCustom {

	Page<User> queryPage(UsernameQueryRequest queryRequest);

	Page<User> queryPage(SysRole role, UserQueryRequest queryRequest);

	Page<User> queryPage(UserQueryRequest queryRequest);

	Page<User> queryArchivedUsers(UserQueryRequest queryRequest);

}
