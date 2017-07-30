package in.clouthink.daas.sbb.storage.local;

import in.clouthink.daas.sbb.storage.LocalfsConfigureProperties;
import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dz
 */
public class StorageServiceLocalFsImpl implements StorageService {

	@Autowired
	private LocalfsConfigureProperties localfsConfigureProperties;

	@Override
	public String resolveImageUrl(String id) {
		return id;
	}

}
