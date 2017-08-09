package in.clouthink.daas.sbb.account.spi;

import in.clouthink.daas.sbb.account.domain.model.AppRole;

/**
 * The AppRole Refz check, if refz found, the AppRole can not be deleted
 *
 * @author dz
 */
public interface AppRoleReference {

	/**
	 * @param target
	 * @return
	 */
	boolean hasReference(AppRole target);

}
