package in.clouthink.daas.sbb.menu.core;

import java.io.File;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

/**
 * @author dz
 */
public final class Menus {

	/**
	 * The root extension point , the extension point can extend from the root extension point
	 */
	public static final String ROOT_EXTENSION_POINT_ID = "extension:menu:root";

	/**
	 * the menu sorter
	 */
	public static Comparator<Menu> MENU_SORTER = (from, to) -> from.getOrder() - to.getOrder();

	private static MenuLoader menuLoader = new MenuJsonLoader();

	/**
	 * @param menuPlugin
	 * @return
	 */
	public static boolean isExtendFromRoot(MenuPlugin menuPlugin) {
		return ROOT_EXTENSION_POINT_ID.equals(menuPlugin.getExtensionPointId());
	}

	/**
	 * @param menuPlugin
	 * @return
	 */
	public static boolean isNotExtendFromRoot(MenuPlugin menuPlugin) {
		return !ROOT_EXTENSION_POINT_ID.equals(menuPlugin.getExtensionPointId());
	}

	/**
	 * @param inputStream
	 * @return
	 */
	public static List<Menu> load(InputStream inputStream) {
		return menuLoader.load(inputStream);
	}

	/**
	 * @param file
	 * @return
	 */
	public static List<Menu> load(File file) {
		return menuLoader.load(file);
	}

}
