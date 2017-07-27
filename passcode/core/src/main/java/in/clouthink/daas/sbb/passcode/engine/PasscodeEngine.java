package in.clouthink.daas.sbb.passcode.engine;


import in.clouthink.daas.sbb.passcode.model.PasscodeRequest;

/**
 * @author dz
 */
public interface PasscodeEngine {

	void handlePasscodeRequest(PasscodeRequest request);

}
