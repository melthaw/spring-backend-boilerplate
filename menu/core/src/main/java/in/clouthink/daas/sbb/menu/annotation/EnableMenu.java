package in.clouthink.daas.sbb.menu.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author dz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MenuConfiguration.class)
public @interface EnableMenu {

	String extensionPointId();

	String pluginId();

	Menu[] menu();

}
