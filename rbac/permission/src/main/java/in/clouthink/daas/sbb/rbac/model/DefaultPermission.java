package in.clouthink.daas.sbb.rbac.model;

/**
 * @author dz
 */
public class DefaultPermission implements Permission {

	/**
	 * The resource instance
	 */
	Resource resource;

	/**
	 * The role which will participate in the resource actions
	 */
	Role role;

	/**
	 * If the action is null or empty , it means any actions is allowed
	 */
	Action[] grantedActions;

	@Override
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public Action[] getGrantedActions() {
		return grantedActions;
	}

	public void setGrantedActions(Action[] grantedActions) {
		this.grantedActions = grantedActions;
	}
}
