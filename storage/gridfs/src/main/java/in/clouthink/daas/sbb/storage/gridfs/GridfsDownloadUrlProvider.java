package in.clouthink.daas.sbb.storage.gridfs;

import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.sbb.storage.exception.FileNotFoundException;
import in.clouthink.daas.sbb.storage.spi.DownloadUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class GridfsDownloadUrlProvider implements DownloadUrlProvider {

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public String getDownloadUrl(String id) {
		FileObject fileObject = fileObjectService.findById(id);
		if (fileObject == null) {
			throw new FileNotFoundException(id);
		}

		return "/api/download/" + fileObject.getFinalFilename();
	}

}
