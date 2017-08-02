package in.clouthink.daas.sbb.rbac.model;

import java.io.Serializable;

/**
 * The Role default impl
 */
public class DefaultRole implements Role, Serializable {

	private String code;

	private String name;

	public DefaultRole() {
	}

	public DefaultRole(String code, String name) {
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
