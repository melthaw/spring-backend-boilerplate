package in.clouthink.daas.sbb.audit.event;

import java.io.Serializable;
import java.util.Date;

/**
 * remove audit(auth) event before specified day
 */
public class RemoveEventObject implements Serializable {

	private String realm;

	private Date beforeDay;

	public RemoveEventObject() {
	}

	public RemoveEventObject(String realm, Date beforeDay) {
		this.realm = realm;
		this.beforeDay = beforeDay;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public Date getBeforeDay() {
		return beforeDay;
	}

	public void setBeforeDay(Date beforeDay) {
		this.beforeDay = beforeDay;
	}
}
