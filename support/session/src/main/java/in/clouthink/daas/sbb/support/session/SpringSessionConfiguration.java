package in.clouthink.daas.sbb.support.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Add the following config to your application.properties or application.yml
 * <p>
 * <code>
 * spring.redis.port=6379
 * spring.redis.host=127.0.0.1
 * spring.redis.password=******
 * spring.redis.pool.max-active=100
 * spring.redis.pool.max-idle=5
 * spring.redis.pool.max-wait=60000
 * <p>
 * spring.session.store-type=redis
 * spring.session.redis.namespace=openapi
 * </code>
 */
@Configuration
@EnableRedisHttpSession
public class SpringSessionConfiguration {

//    @Bean
//    public JedisConnectionFactory connectionFactory() throws Exception {
//        return new JedisConnectionFactory();
//    }

}
