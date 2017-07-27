package in.clouthink.daas.sbb.shared.util;

import in.clouthink.daas.sbb.shared.DomainConstants;
import org.springframework.util.StringUtils;

public class ValidationUtils {

	/**
	 * @param email 可为空,但是格式必须正确
	 */
	public static void validateEmail(String email) {
		if (!StringUtils.isEmpty(email) && DomainConstants.VALID_EMAIL_REGEX.matcher(email).matches()) {
			throw new ValidationException("电子邮箱格式错误.");
		}
	}

	/**
	 * @param cellphone 不能为空且格式必须正确
	 */
	public static void validateCellphone(String cellphone) {
		if (StringUtils.isEmpty(cellphone)) {
			throw new ValidationException("手机号码不能为空.");
		}
		if (!DomainConstants.VALID_CELLPHONE_REGEX.matcher(cellphone).matches()) {
			throw new ValidationException("无效的手机号码.");
		}
	}

}
