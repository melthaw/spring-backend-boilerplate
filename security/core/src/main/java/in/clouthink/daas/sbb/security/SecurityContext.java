package in.clouthink.daas.sbb.security;


/**
 * The security content to get the user
 */
public interface SecurityContext<T> {

	/**
	 * @return current user , or null if not authenticated
	 */
	T currentUser();

	/**
	 * @return the current user
	 * @throw exception if not authenticated
	 */
	T requireUser();

}
