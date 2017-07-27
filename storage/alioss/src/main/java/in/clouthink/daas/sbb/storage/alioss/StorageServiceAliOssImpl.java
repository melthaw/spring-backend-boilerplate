package in.clouthink.daas.sbb.storage.alioss;

import in.clouthink.daas.fss.alioss.support.OssProperties;
import in.clouthink.daas.fss.alioss.support.OssService;
import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.sbb.storage.exception.FileNotFoundException;
import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The alioss impl
 */
public class StorageServiceAliOssImpl implements StorageService {

	@Autowired
	private OssProperties ossProperties;

	@Autowired
	private OssService ossService;

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public String resolveImageUrl(String id) {
		FileObject fileObject = fileObjectService.findById(id);
		if (fileObject == null) {
			throw new FileNotFoundException(id);
		}

		String targetBucketName = ossService.resolveBucket(fileObject);
		return "http://" + targetBucketName + "." + ossProperties.getImgDomain() + "/" + fileObject.getFinalFilename();
	}

}
