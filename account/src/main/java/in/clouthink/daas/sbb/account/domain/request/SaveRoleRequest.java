package in.clouthink.daas.sbb.account.domain.request;

public interface SaveRoleRequest {

	/**
     * Always return the upper cased code , and the code must be unique
     * @return
     */
    String getCode();
    
    String getName();

    String getDescription();
    
}
