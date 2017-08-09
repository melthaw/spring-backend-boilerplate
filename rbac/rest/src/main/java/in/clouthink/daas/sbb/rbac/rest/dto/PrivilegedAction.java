package in.clouthink.daas.sbb.rbac.rest.dto;

import in.clouthink.daas.sbb.rbac.model.Action;
import org.springframework.beans.BeanUtils;

/**
 * @author dz
 */
public class PrivilegedAction {

	public static PrivilegedAction from(Action action) {
		return from(action, false);
	}

	public static PrivilegedAction from(Action action, boolean granted) {
		PrivilegedAction result = new PrivilegedAction();
		BeanUtils.copyProperties(action, result);
		result.setGranted(granted);
		return result;
	}

	private String code;

	private String name;

	private boolean granted;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}
}
