package in.clouthink.daas.sbb.security;

/**
 * The security context to get the authenticated user
 *
 * @author dz
 */
public interface SecurityContext<T> {

	/**
	 * @return current user , or null if not authenticated
	 */
	T currentUser();

	/**
	 * @return the current user
	 * @throw AuthenticationRequiredException if not authenticated
	 */
	T requireUser();

}
