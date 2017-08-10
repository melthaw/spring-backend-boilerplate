package in.clouthink.daas.sbb.sms.aliyun.impl;

public class AliyunOptions {

	private String area;

	private String accessKey;

	private String accessSecret;

	private String signature;

	private String smsEndpoint;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSmsEndpoint() {
		return smsEndpoint;
	}

	public void setSmsEndpoint(String smsEndpoint) {
		this.smsEndpoint = smsEndpoint;
	}
}
