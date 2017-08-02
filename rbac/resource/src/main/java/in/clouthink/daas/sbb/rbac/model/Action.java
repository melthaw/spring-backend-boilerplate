package in.clouthink.daas.sbb.rbac.model;

/**
 * The action def
 */
public interface Action {

	/**
	 * The default helper method to create the action
	 * @param code
	 * @param name
	 * @return
	 */
	static Action from(String code, String name) {
		return new DefaultAction(code, name);
	}

	/**
	 * @return the action code
	 */
	String getCode();

	/**
	 * @return the action name
	 */
	String getName();

}
