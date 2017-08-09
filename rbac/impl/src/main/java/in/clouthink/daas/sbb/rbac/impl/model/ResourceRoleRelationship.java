package in.clouthink.daas.sbb.rbac.impl.model;

import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Document(collection = "ResourceRoleRelationships")
public class ResourceRoleRelationship extends StringIdentifier {

	@Indexed
	private String resourceCode;

	@Indexed
	private String roleCode;

	//the allowed actions code
	private List<String> allowedActions = new ArrayList<>();

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List<String> getAllowedActions() {
		return allowedActions;
	}

	public void setAllowedActions(List<String> allowedActions) {
		this.allowedActions = allowedActions;
	}

}
