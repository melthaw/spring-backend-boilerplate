package in.clouthink.daas.sbb.support.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * How to use the CorsConfiguration & CorsSupportProperties
 * <p>
 * <code>
 * corsConfiguration.setAllowCredentials(true);
 * corsConfiguration.addAllowedOrigin("http://localhost:8090");
 * corsConfiguration.addAllowedHeader("*");
 * corsConfiguration.addAllowedMethod("*");
 * <p>
 * urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
 * </code>
 *
 * @author dz
 */
@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.support.cors")
public class CorsSupportProperties {

    private boolean allowCredentials = true;

    private String allowOrigin = "*";

    private String allowHeader = "*";

    private String allowMethod = "*";

    private int order = Integer.MIN_VALUE;

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getAllowHeader() {
        return allowHeader;
    }

    public void setAllowHeader(String allowHeader) {
        this.allowHeader = allowHeader;
    }

    public String getAllowMethod() {
        return allowMethod;
    }

    public void setAllowMethod(String allowMethod) {
        this.allowMethod = allowMethod;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
