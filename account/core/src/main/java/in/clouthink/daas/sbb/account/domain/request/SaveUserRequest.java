package in.clouthink.daas.sbb.account.domain.request;

import in.clouthink.daas.sbb.account.domain.model.Gender;

import java.util.Date;

/**
 */
public interface SaveUserRequest extends AbstractUserRequest {

	String getUsername();

	String getCellphone();

	Gender getGender();

	String getAvatarId();

	Date getBirthday();

	String getPassword();

}
