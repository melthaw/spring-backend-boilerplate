package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.rest.dto.*;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface SysUserRestSupport {

	Page<SysUserSummary> listSysUsers(SysUserQueryParameter queryRequest);

	SysUserDetail getSysUserDetail(String id);

	SysUser createSysUser(SaveSysUserParameter request);

	void updateSysUser(String id, SaveSysUserParameter request);

	void deleteSysUser(String id, SysUser byWho);

	void changePassword(String id, ChangePasswordRequest request);

	void enableSysUser(String id);

	void disableSysUser(String id);

	void lockSysUser(String id);

	void unlockSysUser(String id);

}
