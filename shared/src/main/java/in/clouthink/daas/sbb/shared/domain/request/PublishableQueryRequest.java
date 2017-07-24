package in.clouthink.daas.sbb.shared.domain.request;

/**
 * publish able query request
 */
public interface PublishableQueryRequest extends PageQueryRequest {

	Boolean getPublished();

}
