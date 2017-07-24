package in.clouthink.daas.sbb.shared.domain.model;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 */
public enum DefaultPasswordEncoder implements PasswordEncoder {
    
    MD5 {
        @Override
        public String encode(String rawPassword, String salt) {
            Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
            return md5PasswordEncoder.encodePassword(rawPassword, salt);
        }
    },
    SHA1 {
        @Override
        public String encode(String rawPassword, String salt) {
            Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
            return md5PasswordEncoder.encodePassword(rawPassword, salt);
        }
    },
    SHA256 {
        @Override
        public String encode(String rawPassword, String salt) {
            Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
            return md5PasswordEncoder.encodePassword(rawPassword, salt);
        }
    }

}
