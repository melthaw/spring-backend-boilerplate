package in.clouthink.daas.sbb.menu.core;

import java.util.Comparator;

/**
 * @author dz
 */
public final class Menus {

	/**
	 * The root extension point , the extension point can extend from the root extension point
	 */
	public static String ROOT_EXTENSION_POINT_ID = "extension:menu:root";

	/**
	 * the menu sorter
	 */
	public static Comparator<Menu> MENU_SORTER = (from, to) -> from.getSort() - to.getSort();

	/**
	 * the menu sorter
	 */
	public static Comparator<MenuPlugin> PLUGIN_SORTER = (from, to) -> from.getMenu().getSort() -
																	   to.getMenu().getSort();

	/**
	 * @param menuPlugin
	 * @return
	 */
	public static boolean isExtendFromRoot(MenuPlugin menuPlugin) {
		return ROOT_EXTENSION_POINT_ID.equals(menuPlugin.getExtensionId());
	}

	/**
	 * @param menuPlugin
	 * @return
	 */
	public static boolean isNotExtendFromRoot(MenuPlugin menuPlugin) {
		return !ROOT_EXTENSION_POINT_ID.equals(menuPlugin.getExtensionId());
	}

}
