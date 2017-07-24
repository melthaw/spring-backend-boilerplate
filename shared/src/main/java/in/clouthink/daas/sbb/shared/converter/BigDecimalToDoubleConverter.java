package in.clouthink.daas.sbb.shared.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * BigDecimal To Double Converter
 */
@Component
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {

	@Override
	public Double convert(BigDecimal source) {
		return source.doubleValue();
	}

}
