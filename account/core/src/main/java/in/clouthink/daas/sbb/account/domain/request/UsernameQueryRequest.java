package in.clouthink.daas.sbb.account.domain.request;


import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

public interface UsernameQueryRequest extends DateRangedQueryRequest {

	/**
	 * 用户名
	 *
	 * @return
	 */
	String getUsername();

}
