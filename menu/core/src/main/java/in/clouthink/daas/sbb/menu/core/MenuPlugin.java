package in.clouthink.daas.sbb.menu.core;

/**
 * @author dz
 */
public interface MenuPlugin {

	String getPluginId();

	String getExtensionId();

	Menu getMenu();

}
