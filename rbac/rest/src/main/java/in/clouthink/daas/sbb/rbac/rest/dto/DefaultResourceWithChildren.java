package in.clouthink.daas.sbb.rbac.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.clouthink.daas.sbb.rbac.model.DefaultResource;

import java.util.ArrayList;
import java.util.List;

/**
 * The resource tree struct impl
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultResourceWithChildren extends DefaultResource {

	private List<DefaultResourceWithChildren> children = new ArrayList<>();

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	@JsonDeserialize(contentAs = DefaultResourceWithChildren.class)
	public List<DefaultResourceWithChildren> getChildren() {
		return children;
	}

	public void setChildren(List<DefaultResourceWithChildren> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return super.toString() + "/DefaultResourceWithChildren{" +
			   "children=" + children +
			   '}';
	}
}
