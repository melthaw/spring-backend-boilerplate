package in.clouthink.daas.sbb.rbac.model;

/**
 * The role def
 */
public interface Role {

	/**
	 * @return the role code (unique in global)
	 */
	String getCode();

	/**
	 * @return the role name
	 */
	String getName();

}
