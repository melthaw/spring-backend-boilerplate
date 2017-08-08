package in.clouthink.daas.sbb.account.rest.support.mock;

import in.clouthink.daas.sbb.account.rest.dto.UserDetail;
import in.clouthink.daas.sbb.account.rest.dto.UserQueryParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserSummary;
import in.clouthink.daas.sbb.account.rest.support.ArchivedUserRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * ArchivedUserRestSupport mocker
 *
 * @author dz
 */
@Component
public class ArchivedUserRestSupportMockImpl implements ArchivedUserRestSupport {

	@Override
	public Page<UserSummary> listArchivedUsers(UserQueryParameter queryRequest) {
		return null;
	}

	@Override
	public UserDetail getArchivedUser(String id) {
		return null;
	}
}
