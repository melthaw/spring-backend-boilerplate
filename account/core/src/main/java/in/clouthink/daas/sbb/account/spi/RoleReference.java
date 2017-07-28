package in.clouthink.daas.sbb.account.spi;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;

/**
 * @author dz
 */
public interface RoleReference {

	/**
	 *
	 * @param target
	 * @return
	 */
	boolean hasReference(SysRole target);

	/**
	 *
	 * @param target
	 * @return
	 */
	boolean hasReference(SysExtRole target);

}
