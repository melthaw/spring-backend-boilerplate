package in.clouthink.daas.sbb.menu;

/**
 * @author dz
 */
public interface MenuPlugin {

	String getPluginId();

	String getExtensionId();

	Menu getMenu();

}
