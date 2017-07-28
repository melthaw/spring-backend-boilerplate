package in.clouthink.daas.sbb.openapi.controller;

import in.clouthink.daas.sbb.account.dto.Cellphone;
import in.clouthink.daas.sbb.account.dto.ForgetPasswordParameter;
import in.clouthink.daas.sbb.account.dto.RegisterParameter;
import in.clouthink.daas.sbb.openapi.support.GuestRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dz
 */
@Api("应用用户注册&找回密码等")
@RestController
@RequestMapping("/api")
public class GuestRestController {

	@Autowired
	private GuestRestSupport appUserGuestRestSupport;

	@ApiOperation(value = "获取注册的验证码")
	@RequestMapping(value = "/guest/register/passcode", method = RequestMethod.POST)
	public void getPasscode4Register(@RequestBody Cellphone cellphone) {
		appUserGuestRestSupport.getPasscode4Register(cellphone);
	}

	@ApiOperation(value = "注册新用户")
	@RequestMapping(value = "/guest/register", method = RequestMethod.POST)
	public void register(@RequestBody RegisterParameter registerParameter) {
		appUserGuestRestSupport.register(registerParameter);
	}

	@ApiOperation(value = "获取找回密码的验证码")
	@RequestMapping(value = "/guest/forgetPassword/passcode", method = RequestMethod.POST)
	public void getPasscode4ForgetPassword(@RequestBody Cellphone cellphone) {
		appUserGuestRestSupport.getPasscode4ForgetPassword(cellphone);
	}

	@ApiOperation(value = "重置密码")
	@RequestMapping(value = "/guest/forgetPassword", method = RequestMethod.POST)
	public void forgetPassword(@RequestBody ForgetPasswordParameter forgetPasswordParameter) {
		appUserGuestRestSupport.forgetPassword(forgetPasswordParameter);
	}

}
