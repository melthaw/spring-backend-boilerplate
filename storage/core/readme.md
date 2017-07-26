# spring-boot programing sample

	public static void main(String[] args) throws Exception {
		SpringApplication.run(
				new Object[]{AttachmentModuleConfiguration.class},
				args);
	}
	
# application.properties configuration
 
    cn.gyming.bodydream.attachment.alioss.keyId=JesoPOgUS50bgImX
    cn.gyming.bodydream.attachment.alioss.secret=pu6qIcRzkBOCA3wNaXfdfZ9Aa3MPof
    cn.gyming.bodydream.attachment.alioss.ossDomain=oss-cn-shenzhen.aliyuncs.com
    cn.gyming.bodydream.attachment.alioss.imgDomain=img-cn-shenzhen.aliyuncs.com
    cn.gyming.bodydream.attachment.alioss.defaultBucket=gyming-prd
    cn.gyming.bodydream.attachment.alioss.buckets.image=gyming-img
