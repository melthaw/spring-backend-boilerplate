package in.clouthink.daas.sbb.rbac.model;

import java.io.Serializable;

/**
 * The abstraction for code (with type)
 */
public class TypedCode implements Serializable {

	/*
	 * The name() of code
	 */
	private String type;

	/*
	 * The code of RoleType
	 */
	private String code;

	public TypedCode(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

}
