package in.clouthink.daas.sbb.rbac.support.parser;

/**
 * The role parser abstraction
 */
public interface RoleParser<T> {

	/**
	 * take the role code and return the underlying role instance
	 *
	 * @param roleCode
	 * @return
	 */
	T parse(String roleCode);

}
