package in.clouthink.daas.sbb.support.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * The CORS support configuration. The cors is auto enabled if the CORS related configuration found in the application.(yml or properties)
 *
 * @author dz
 */
@Configuration
@EnableConfigurationProperties(CorsSupportProperties.class)
public class CorsSupportConfiguration {

    //refer to : http://spring.io/blog/2015/06/08/cors-support-in-spring-framework
    @Bean
    @ConditionalOnExpression("${in.clouthink.daas.sbb.support.cors.enabled:true}")
    @Autowired
    public FilterRegistrationBean filterRegistrationBean(CorsSupportProperties corsSupportProperties) {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(corsSupportProperties.isAllowCredentials());
        corsConfiguration.addAllowedOrigin(corsSupportProperties.getAllowOrigin());
        corsConfiguration.addAllowedHeader(corsSupportProperties.getAllowHeader());
        corsConfiguration.addAllowedMethod(corsSupportProperties.getAllowMethod());

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
        FilterRegistrationBean registration = new FilterRegistrationBean(corsFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(corsSupportProperties.getOrder());
        return registration;
    }

}
