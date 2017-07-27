package in.clouthink.daas.sbb.storage.gridfs;

import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.sbb.storage.exception.FileNotFoundException;
import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class StorageServiceGridfsImpl implements StorageService {

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public String resolveImageUrl(String id) {
		FileObject fileObject = fileObjectService.findById(id);
		if (fileObject == null) {
			throw new FileNotFoundException(id);
		}

		return "/api/download/" + fileObject.getFinalFilename();
	}

}
