package in.clouthink.daas.sbb.rbac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Permission default impl
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultPermission implements Permission {

	/*
	 * The resource code
	 */
	private Resource resource;

	/*
	 * The role code
	 */
	private String role;

	/*
	 * The granted actions
	 */
	private Action[] grantedActions;

	@Override
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
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
