package in.clouthink.daas.sbb.menu.annotation;

import java.lang.annotation.*;

/**
 * @author dz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Metadata {

	enum ValueType {
		String, Boolean, Short, Integer, Long, BigDecimal;
	}

	String key();

	String value();

	ValueType type() default ValueType.String;

}
