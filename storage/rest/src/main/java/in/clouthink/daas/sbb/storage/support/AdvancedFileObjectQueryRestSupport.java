package in.clouthink.daas.sbb.storage.support;

import in.clouthink.daas.fss.mongodb.model.FileObject;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.storage.dto.DefaultFileObjectQueryParameter;
import org.springframework.data.domain.Page;

/**
 */
public interface AdvancedFileObjectQueryRestSupport {

	void deleteById(String id, User user);

	Page<FileObject> listFileObject(DefaultFileObjectQueryParameter queryParameter, User user);

}
