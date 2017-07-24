package in.clouthink.daas.sbb.shared.domain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ValueEnumSerializer extends JsonSerializer<ValueProvider<?>> {

	@Override
	public void serialize(ValueProvider<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (StringValueProvider.class.isAssignableFrom(value.getClass())) {
			jgen.writeString((String) value.getValue());
		} else if (IntValueProvider.class.isAssignableFrom(value.getClass())) {
			jgen.writeNumber((Integer) value.getValue());
		} else {
			throw new IllegalArgumentException(
					String.format("Unsupported value enum type: %s", value.getClass().getName()));
		}
	}

}
