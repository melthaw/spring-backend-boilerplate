package in.clouthink.daas.sbb.dashboard.mvc.controller;

import in.clouthink.daas.sbb.dashboard.rest.support.SystemSettingRestSupport;
import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.shared.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {

	@Autowired
	private SystemSettingRestSupport systemSettingRestSupport;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (UserAgentUtils.getBrowserFamily(userAgent).contains("IE")) {
			return "redirect:/browsers";
		}

		return "redirect:/dashboard";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (UserAgentUtils.getBrowserFamily(userAgent).contains("IE")) {
			return "redirect:/browsers";
		}

		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);

		return "login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {
		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);
		return "dashboard";
	}

	@RequestMapping(value = "/browsers", method = RequestMethod.GET)
	public String browserDownload(Model model) {
		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);

		return "browsers";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String e403(Model model) {
		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);
		return "403";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String e404(Model model) {
		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);
		return "404";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String e500(Model model) {
		SystemSetting systemSetting = systemSettingRestSupport.getSystemSetting();
		model.addAttribute("systemSetting", systemSetting);
		return "500";
	}

}
