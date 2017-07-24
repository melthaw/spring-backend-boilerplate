package in.clouthink.daas.sbb.account.domain.request;

import in.clouthink.daas.sbb.account.domain.model.Gender;

import java.util.Date;

/**
 */
public interface SaveAppUserRequest extends AbstractUserRequest {

	String getDisplayName();

	String getUsername();

	Gender getGender();

	Date getBirthday();

	String getPassword();

	String getAvatarId();

	String getAvatarUrl();

	String getProvince();

	String getCity();

	String getSignature();
}
