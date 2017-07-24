package in.clouthink.daas.sbb.shared.domain.model;

/**
 */
public interface PasswordEncoder {

	String encode(String rawPassword, String salt);

}
