package in.clouthink.daas.sbb.account.spi;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;

/**
 * The ExtRole Refz check, if refz found, the ExtRole can not be deleted
 *
 * @author dz
 */
public interface ExtRoleReference {

	/**
	 *
	 * @param target
	 * @return
	 */
	boolean hasReference(ExtRole target);

}
