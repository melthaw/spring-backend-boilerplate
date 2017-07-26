package in.clouthink.daas.sbb.audit.domain.request;

import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

/**
 * @author dz
 */
public interface AuthEventQueryRequest extends DateRangedQueryRequest {

	/**
	 * @return never null
	 */
	String getRealm();

	/**
	 * @return who request the authentication
	 */
	String getUsername();

	/**
	 * @return
	 */
	Boolean getSucceed();

}
