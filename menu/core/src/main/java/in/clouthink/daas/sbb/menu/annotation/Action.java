package in.clouthink.daas.sbb.menu.annotation;

import java.lang.annotation.*;

/**
 * @author dz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

	String code();

	String name();

}
