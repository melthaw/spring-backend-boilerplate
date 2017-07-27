package in.clouthink.daas.sbb.passcode.service;

/**
 * @author dz
 */
public interface PasscodeService {

	void sendPasscode4Register(String cellphone);

	void sendPasscode4ForgetPassword(String cellphone);

	void validatePasscode4Register(String cellphone, String passcode);

	void validatePasscode4ForgetPassword(String cellphone, String passcode);

}
