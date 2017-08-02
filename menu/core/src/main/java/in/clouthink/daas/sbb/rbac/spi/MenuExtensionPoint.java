package in.clouthink.daas.sbb.rbac.spi;

/**
 * @author dz
 */
public interface MenuExtensionPoint {

	/**
	 * The root extension point , the extension point can extend from the root extension point
	 */
	String ROOT_EXTENSION_POINT_ID = "resource:menu:root";

	/**
	 *
	 * @return the extension id
	 */
	String getExtensionId();

	ResourceProvider getExtensionPointService();

}
