package in.clouthink.daas.sbb.rbac.model;


import java.util.List;

/**
 * The module with children presents the tree structure for resource
 */
public interface ResourceWithChildren extends Resource {

	/**
	 * @return false if no children available
	 */
	boolean hasChildren();

	/**
	 * @return
	 */
	List<ResourceWithChildren> getChildren();

}
