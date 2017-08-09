package in.clouthink.daas.sbb.rbac.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * privileged resource tree
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivilegedResourceWithChildren extends PrivilegedResource {

	public static PrivilegedResourceWithChildren from(Resource resource) {
		PrivilegedResourceWithChildren result = new PrivilegedResourceWithChildren();
		BeanUtils.copyProperties(resource, result, "actions");
		result.setActions(resource.getActions().stream().map(PrivilegedAction::from).collect(Collectors.toList()));
		return result;
	}

	private List<PrivilegedResourceWithChildren> children = new ArrayList<>();

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	@JsonDeserialize(contentAs = PrivilegedResourceWithChildren.class)
	public List<PrivilegedResourceWithChildren> getChildren() {
		return children;
	}

	public void setChildren(List<PrivilegedResourceWithChildren> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return super.toString() + "/DefaultResourceWithChildren{" +
			   "children=" + children +
			   '}';
	}

}
