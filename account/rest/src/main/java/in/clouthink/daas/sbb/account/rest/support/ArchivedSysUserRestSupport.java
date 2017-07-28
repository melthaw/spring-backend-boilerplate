package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.rest.dto.SysUserDetail;
import in.clouthink.daas.sbb.account.rest.dto.SysUserQueryParameter;
import in.clouthink.daas.sbb.account.rest.dto.SysUserSummary;
import org.springframework.data.domain.Page;

/**
 */
public interface ArchivedSysUserRestSupport {

	Page<SysUserSummary> listArchivedUsers(SysUserQueryParameter queryRequest);

	SysUserDetail getArchivedUser(String id);

}
