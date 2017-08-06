package in.clouthink.daas.sbb.rbac.model;

/**
 * The permission def
 */
public interface Permission {

	/**
	 * @return The resource instance
	 */
	Resource getResource();

	/**
	 * @return The role which will participate in the resource actions
	 */
	Role getRole();

	/**
	 * @return If the action is null or empty , it means any actions is allowed
	 */
	Action[] getGrantedActions();

}
