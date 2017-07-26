package in.clouthink.daas.sbb.account.repository.custom;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.UsernameQueryRequest;
import org.springframework.data.domain.Page;

/**
 * 系统用户持久层扩展
 *
 * @author dz
 */
public interface SysUserRepositoryCustom {

	Page<SysUser> queryPage(UsernameQueryRequest queryRequest);

	Page<SysUser> queryPage(SysRole role, SysUserQueryRequest queryRequest);

	Page<SysUser> queryPage(SysUserQueryRequest queryRequest);

	Page<SysUser> queryArchivedUsers(SysUserQueryRequest queryRequest);

}
