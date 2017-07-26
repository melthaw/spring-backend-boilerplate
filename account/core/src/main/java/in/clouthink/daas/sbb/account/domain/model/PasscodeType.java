package in.clouthink.daas.sbb.account.domain.model;

/**
 * @author dz
 */
public enum PasscodeType {

	//注册
	REGISTER,
	//忘记密码
	FORGET_PASSWORD;

	String EVENT_GROUP_NAME = "passcode";

}
