package in.clouthink.daas.sbb.shared.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Double To BigDecimal Converter
 */
@Component
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

	@Override
	public BigDecimal convert(Double source) {
		return new BigDecimal(source).setScale(2, RoundingMode.HALF_UP);
	}

}
