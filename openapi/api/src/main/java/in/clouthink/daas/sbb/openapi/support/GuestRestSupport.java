package in.clouthink.daas.sbb.openapi.support;

import in.clouthink.daas.sbb.account.dto.Cellphone;
import in.clouthink.daas.sbb.account.dto.ForgetPasswordParameter;
import in.clouthink.daas.sbb.account.dto.RegisterParameter;

/**
 * @author dz
 */
public interface GuestRestSupport {

	void getPasscode4Register(Cellphone cellphone);

	void register(RegisterParameter registerParameter);

	void getPasscode4ForgetPassword(Cellphone cellphone);

	void forgetPassword(ForgetPasswordParameter forgetPasswordParameter);
}
