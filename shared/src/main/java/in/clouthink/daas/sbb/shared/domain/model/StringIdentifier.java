package in.clouthink.daas.sbb.shared.domain.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public abstract class StringIdentifier implements Serializable {

	public static String trim(String value) {
		if (value != null) {
			return value.trim();
		}

		return value;
	}

	@Id
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
