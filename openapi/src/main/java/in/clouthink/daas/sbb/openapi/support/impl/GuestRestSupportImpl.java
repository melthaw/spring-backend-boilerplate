package in.clouthink.daas.sbb.openapi.support.impl;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.Cellphone;
import in.clouthink.daas.sbb.account.dto.ForgetPasswordParameter;
import in.clouthink.daas.sbb.account.dto.RegisterParameter;
import in.clouthink.daas.sbb.account.dto.SaveAppUserParameter;
import in.clouthink.daas.sbb.account.exception.AppUserException;
import in.clouthink.daas.sbb.account.exception.RegisterException;
import in.clouthink.daas.sbb.account.service.AppUserAccountService;
import in.clouthink.daas.sbb.account.service.PasscodeService;
import in.clouthink.daas.sbb.openapi.support.GuestRestSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author dz
 */
@Component
public class GuestRestSupportImpl implements GuestRestSupport {

	@Autowired
	private AppUserAccountService appUserAccountService;

	@Autowired
	private PasscodeService passcodeService;

	@Override
	public void getPasscode4Register(Cellphone cellphone) {
		AppUser appUser = appUserAccountService.findAccountByCellphone(cellphone.getCellphone());
		if (appUser != null) {
			throw new RegisterException("该手机号已注册");
		}

		passcodeService.sendPasscode4Register(cellphone.getCellphone());
	}

	@Override
	public void register(RegisterParameter registerParameter) {
		passcodeService.validatePasscode4Register(registerParameter.getCellphone(), registerParameter.getPasscode());
		if (StringUtils.isEmpty(registerParameter.getPassword())) {
			throw new RegisterException("密码不能为空");
		}

		SaveAppUserParameter saveAppUserParameter = new SaveAppUserParameter();
		BeanUtils.copyProperties(registerParameter, saveAppUserParameter);
		saveAppUserParameter.setUsername(registerParameter.getCellphone());

		appUserAccountService.createAccount(saveAppUserParameter, AppRole.ROLE_USER);
	}

	@Override
	public void getPasscode4ForgetPassword(Cellphone cellphone) {
		AppUser appUser = appUserAccountService.findAccountByCellphone(cellphone.getCellphone());
		if (appUser == null) {
			throw new AppUserException("该手机号未注册");
		}
		passcodeService.sendPasscode4ForgetPassword(cellphone.getCellphone());
	}

	@Override
	public void forgetPassword(ForgetPasswordParameter forgetPasswordParameter) {
		passcodeService.validatePasscode4ForgetPassword(forgetPasswordParameter.getCellphone(),
														forgetPasswordParameter.getPasscode());

		AppUser appUser = appUserAccountService.findAccountByCellphone(forgetPasswordParameter.getCellphone());
		if (appUser == null) {
			throw new AppUserException("该手机号尚未注册!");
		}
		appUserAccountService.changePassword(appUser.getId(), forgetPasswordParameter.getNewPassword());

	}
}
