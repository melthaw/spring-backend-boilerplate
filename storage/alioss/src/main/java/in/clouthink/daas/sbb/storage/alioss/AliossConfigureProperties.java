package in.clouthink.daas.sbb.storage.alioss;

import in.clouthink.daas.fss.alioss.support.impl.DefaultOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.storage.alioss")
public class AliossConfigureProperties extends DefaultOssProperties {

}
