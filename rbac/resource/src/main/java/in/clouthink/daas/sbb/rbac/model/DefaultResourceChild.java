package in.clouthink.daas.sbb.rbac.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * The ResourceChild default impl.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultResourceChild extends DefaultResource implements MutableResourceChild, Serializable {

	private String parentCode;

	@Override
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
