package in.clouthink.daas.sbb.menu.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable the menu plugin, it means it's a menu plugin , can be registered into the repository by system automatically.
 *
 * @author dz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableMenuImportSelector.class)
public @interface EnableMenu {

	/**
	 * @return what the plugin extends from.
	 */
	String extensionPointId();

	/**
	 * @return the plugin id of the menu plugin
	 */
	String pluginId();

	/**
	 * @return the menus provided by the plugin
	 */
	Menu[] menu();

}
