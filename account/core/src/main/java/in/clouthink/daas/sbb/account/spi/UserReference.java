package in.clouthink.daas.sbb.account.spi;

import in.clouthink.daas.sbb.account.domain.model.User;

/**
 * The User Refz check, if refz found, the User can not be deleted
 *
 * @author dz
 */
public interface UserReference {

	/**
	 * @param target
	 * @return
	 */
	boolean hasReference(User target);

}
