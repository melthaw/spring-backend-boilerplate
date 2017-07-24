package in.clouthink.daas.sbb.openapi.support;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.openapi.dto.DefaultFileObjectQueryParameter;
import in.clouthink.daas.fss.mongodb.model.FileObject;
import org.springframework.data.domain.Page;

/**
 */
public interface AdvancedFileObjectQueryRestSupport {

	void deleteById(String id, AppUser user);

	Page<FileObject> listFileObject(DefaultFileObjectQueryParameter queryParameter, AppUser user);

}
