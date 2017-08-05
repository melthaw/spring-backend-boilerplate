package in.clouthink.daas.sbb.rbac.rest.dto;

/**
 * @author dz
 */
public class GrantResourceParameter {

	private String resourceCode;

	private String[] actionCodes = new String[0];

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String[] getActionCodes() {
		return actionCodes;
	}

	public void setActionCodes(String[] actionCodes) {
		this.actionCodes = actionCodes;
	}
}
