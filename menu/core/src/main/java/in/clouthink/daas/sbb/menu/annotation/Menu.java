package in.clouthink.daas.sbb.menu.annotation;

import java.lang.annotation.*;

/**
 * @author dz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Menu {

	boolean virtual() default false;

	boolean open() default false;

	String code();

	String name();

	int order() default Integer.MAX_VALUE;

	String[] patterns() default {};

	Action[] actions() default {};

	Metadata[] metadata() default {};

	ExtensionPoint[] extensionPoint() default {};

}
