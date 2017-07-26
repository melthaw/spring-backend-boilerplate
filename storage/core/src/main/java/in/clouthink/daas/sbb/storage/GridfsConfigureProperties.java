package in.clouthink.daas.sbb.storage;

import in.clouthink.daas.fss.alioss.support.impl.DefaultOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.appt.storage.gridfs")
public class GridfsConfigureProperties extends DefaultOssProperties {

}
