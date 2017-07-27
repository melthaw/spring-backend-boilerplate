package in.clouthink.daas.sbb.storage.alioss;

import com.aliyun.oss.OSSClient;
import in.clouthink.daas.sbb.storage.service.StorageService;
import in.clouthink.daas.fss.alioss.spiImpl.FileStorageServiceImpl;
import in.clouthink.daas.fss.alioss.support.OssService;
import in.clouthink.daas.fss.alioss.support.impl.OssServiceImpl;
import in.clouthink.daas.fss.mongodb.spiImpl.FileObjectServiceImpl;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.fss.spi.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableConfigurationProperties(AliossConfigureProperties.class)
public class AliossModuleConfiguration {

	@Bean
	@Autowired
	public OSSClient ossClient(AliossConfigureProperties properties) {
		return new OSSClient("http://" + properties.getOssDomain(), properties.getKeyId(), properties.getSecret());
	}

	@Bean
	public OssService ossStrategy() {
		return new OssServiceImpl();
	}

	@Bean
	public FileObjectService fileObjectService() {
		return new FileObjectServiceImpl();
	}

	@Bean
	public FileStorageService fileStorageService() {
		return new FileStorageServiceImpl();
	}

	@Bean
	public StorageService storageService() {
		return new StorageServiceAliOssImpl();
	}
}
