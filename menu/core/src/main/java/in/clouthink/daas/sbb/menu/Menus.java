package in.clouthink.daas.sbb.menu;

import java.util.Comparator;

/**
 * @author dz
 */
public final class Menus {

	/**
	 * The root extension point , the extension point can extend from the root extension point
	 */
	public static String ROOT_EXTENSION_POINT_ID = "resource:menu:root";

	/**
	 * the menu sorter
	 */
	public static Comparator<Menu> MENU_SORTER = (from, to) -> from.getSort() - to.getSort();

	/**
	 * @param menuPlugin
	 * @return
	 */
	public static boolean isExtendFromRoot(MenuPlugin menuPlugin) {
		return ROOT_EXTENSION_POINT_ID.equals(menuPlugin.getExtensionId());
	}

}
