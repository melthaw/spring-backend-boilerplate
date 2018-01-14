package in.clouthink.daas.sbb.support.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(CorsSupportProperties.class)
public class CorsSupportConfiguration {

    @Autowired
    private CorsSupportProperties corsSupportProperties;

    //refer to : http://spring.io/blog/2015/06/08/cors-support-in-spring-framework
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
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
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
