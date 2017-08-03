package in.clouthink.daas.sbb.menu.core;

import java.util.List;

/**
 * @author dz
 */
public interface MenuPlugin {

	String getPluginId();

	String getExtensionPointId();

	List<Menu> getMenu();

}
