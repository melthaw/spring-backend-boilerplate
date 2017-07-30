package in.clouthink.daas.sbb.account.domain.request;


import in.clouthink.daas.sbb.account.domain.model.Gender;

public interface UserQueryRequest extends UsernameQueryRequest {

	/**
	 * @return 联系电话
	 */
	String getCellphone();

	/**
	 * @return 电子邮箱
	 */
	String getEmail();

	/**
	 * @return 是否启用(或禁用)
	 */
	Boolean getEnabled();

	/**
	 * @return 显示的用户名
	 */
	String getDisplayName();

	/**
	 * @return 性别
	 */
	Gender getGender();

}
