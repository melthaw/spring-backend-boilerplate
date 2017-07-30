package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.rest.dto.UserDetail;
import in.clouthink.daas.sbb.account.rest.dto.UserQueryParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserSummary;
import org.springframework.data.domain.Page;

/**
 */
public interface ArchivedUserRestSupport {

	Page<UserSummary> listArchivedUsers(UserQueryParameter queryRequest);

	UserDetail getArchivedUser(String id);

}
