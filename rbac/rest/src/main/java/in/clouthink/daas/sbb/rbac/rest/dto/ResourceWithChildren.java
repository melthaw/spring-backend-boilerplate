package in.clouthink.daas.sbb.rbac.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.clouthink.daas.sbb.rbac.model.Resource;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The resource tree struct impl
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceWithChildren extends ResourceSummary {

	public static ResourceWithChildren from(Resource resource) {
		ResourceWithChildren result = new ResourceWithChildren();
		BeanUtils.copyProperties(resource, result);
		return result;
	}

	private List<ResourceWithChildren> children = new ArrayList<>();

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	@JsonDeserialize(contentAs = ResourceWithChildren.class)
	public List<ResourceWithChildren> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceWithChildren> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return super.toString() + "/DefaultResourceWithChildren{" +
			   "children=" + children +
			   '}';
	}

}
