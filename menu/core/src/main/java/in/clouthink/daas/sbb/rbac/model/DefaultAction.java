package in.clouthink.daas.sbb.rbac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * The action default impl
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultAction implements Action, Serializable {

	private String code;

	private String name;

	public DefaultAction() {
	}

	public DefaultAction(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
