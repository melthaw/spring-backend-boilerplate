package in.clouthink.daas.sbb.audit.domain.model;

import in.clouthink.daas.audit.core.MutableAuditEvent;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author dz
 */
@Document(collection = "AuditEvents")
public class AuditEvent extends StringIdentifier implements MutableAuditEvent<String> {

	@Indexed
	private String realm;

	@Indexed
	private String requestedBy;

	@Indexed
	private Date requestedAt;

	private String userAgent;

	private String httpMethod;

	private String requestedUrl;

	private String clientAddress;

	private String xForwardedFor;

	private String serviceName;

	private String methodName;

	private String category;

	private String description;

	private long timeCost;

	@Indexed
	private boolean error;

	private String errorMessage;

	private String errorDetail;

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public String getRequestedBy() {
		return requestedBy;
	}

	@Override
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	@Override
	public Date getRequestedAt() {
		return requestedAt;
	}

	@Override
	public void setRequestedAt(Date requestedAt) {
		this.requestedAt = requestedAt;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String getHttpMethod() {
		return httpMethod;
	}

	@Override
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	@Override
	public String getRequestedUrl() {
		return requestedUrl;
	}

	@Override
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	@Override
	public String getClientAddress() {
		return clientAddress;
	}

	@Override
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	@Override
	public String getForwardedFor() {
		return xForwardedFor;
	}

	@Override
	public void setForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public long getTimeCost() {
		return timeCost;
	}

	@Override
	public void setTimeCost(long timeCost) {
		this.timeCost = timeCost;
	}

	@Override
	public boolean hasError() {
		return error;
	}

	public boolean isError() {
		return error;
	}

	@Override
	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorDetail() {
		return errorDetail;
	}

	@Override
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	@Override
	public String toString() {
		return "DefaultAuditEvent{" +
			   "requestedBy='" + requestedBy + '\'' +
			   ", requestedAt=" + requestedAt +
			   ", userAgent='" + userAgent + '\'' +
			   ", httpMethod='" + httpMethod + '\'' +
			   ", requestedUrl='" + requestedUrl + '\'' +
			   ", clientAddress='" + clientAddress + '\'' +
			   ", xForwardedFor='" + xForwardedFor + '\'' +
			   ", controllerName='" + serviceName + '\'' +
			   ", methodName='" + methodName + '\'' +
			   ", category='" + category + '\'' +
			   ", description='" + description + '\'' +
			   ", timeCost=" + timeCost +
			   ", error=" + error +
			   ", errorMessage='" + errorMessage + '\'' +
			   ", errorDetail='" + errorDetail + '\'' +
			   '}';
	}
}
