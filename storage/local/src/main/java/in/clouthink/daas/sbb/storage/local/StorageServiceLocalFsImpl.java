package in.clouthink.daas.sbb.storage.local;

import in.clouthink.daas.sbb.storage.service.StorageService;

/**
 * @author dz
 */
public class StorageServiceLocalFsImpl implements StorageService {

	@Override
	public String resolveImageUrl(String id) {
		return id;
	}
}
