package in.clouthink.daas.sbb.account.passcode.engine;


import in.clouthink.daas.sbb.account.domain.model.PasscodeRequest;

/**
 * @author dz
 */
public interface PasscodeEngine {

	void handlePasscodeRequest(PasscodeRequest request);

}
