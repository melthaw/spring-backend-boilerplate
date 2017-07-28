package in.clouthink.daas.sbb.sms.service;

public interface SmsService {

	void sendPasscode4Register(String cellphone, String passcode);

	void sendPasscode4ForgetPassword(String cellphone, String passcode);

}
