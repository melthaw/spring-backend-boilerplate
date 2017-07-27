package in.clouthink.daas.sbb.passcode.service.impl;

import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.sbb.account.exception.PasscodeException;
import in.clouthink.daas.sbb.passcode.model.Passcode;
import in.clouthink.daas.sbb.passcode.model.PasscodeRequest;
import in.clouthink.daas.sbb.passcode.model.Passcodes;
import in.clouthink.daas.sbb.passcode.repository.PasscodeRepository;
import in.clouthink.daas.sbb.passcode.service.PasscodeService;
import in.clouthink.daas.sbb.shared.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author dz
 */
@Service
public class PasscodeServiceImpl implements PasscodeService {

	@Autowired
	private PasscodeRepository passcodeRepository;

	@Override
	public void sendPasscode4Register(String cellphone) {
		ValidationUtils.validateCellphone(cellphone);

		PasscodeRequest passcodeRequest = new PasscodeRequest();
		passcodeRequest.setCellphone(cellphone);
		passcodeRequest.setCategory(Passcodes.REGISTER);

		Edms.getEdm(Passcodes.EVENT_GROUP_NAME).dispatch(Passcodes.REGISTER, passcodeRequest);
	}

	@Override
	public void sendPasscode4ForgetPassword(String cellphone) {
		ValidationUtils.validateCellphone(cellphone);

		PasscodeRequest passcodeRequest = new PasscodeRequest();
		passcodeRequest.setCellphone(cellphone);
		passcodeRequest.setCategory(Passcodes.FORGET_PASSWORD);

		Edms.getEdm(Passcodes.EVENT_GROUP_NAME).dispatch(Passcodes.FORGET_PASSWORD, passcodeRequest);
	}

	@Override
	public void validatePasscode4Register(String cellphone, String code) {
		ValidationUtils.validateCellphone(cellphone);

		if (StringUtils.isEmpty(code)) {
			throw new PasscodeException("请提供验证码");
		}

		Passcode passcode = passcodeRepository.findByCellphoneAndCategory(cellphone, Passcodes.REGISTER);
		if (passcode == null) {
			throw new PasscodeException("无效的验证请求");
		}

		if (!passcode.getPasscode().equalsIgnoreCase(code)) {
			throw new PasscodeException("无效的验证码");
		}

		if (passcode.getWhenToExpire() < System.currentTimeMillis()) {
			throw new PasscodeException("验证码已过期");
		}
	}

	@Override
	public void validatePasscode4ForgetPassword(String cellphone, String code) {
		ValidationUtils.validateCellphone(cellphone);

		if (StringUtils.isEmpty(code)) {
			throw new PasscodeException("请提供验证码");
		}
		Passcode passcode = passcodeRepository.findByCellphoneAndCategory(cellphone, Passcodes.FORGET_PASSWORD);
		if (passcode == null) {
			throw new PasscodeException("无效的验证请求");
		}

		if (!passcode.getPasscode().equalsIgnoreCase(code)) {
			throw new PasscodeException("无效的验证码");
		}

		if (passcode.getWhenToExpire() < System.currentTimeMillis()) {
			throw new PasscodeException("验证码已过期");
		}
	}

}
