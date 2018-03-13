package in.clouthink.daas.sbb.support.security;

import in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionHandler;
import in.clouthink.daas.sbb.security.impl.spring.UserDetailsAuthenticationProviderImpl;
import in.clouthink.daas.sbb.security.impl.spring.UserDetailsServiceImpl;
import in.clouthink.daas.sbb.security.impl.spring.rest.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class OpenApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new UserDetailsAuthenticationProviderImpl();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandlerImpl() {
        return new AuthenticationSuccessHandlerRestImpl();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandlerImpl() {
        return new AuthenticationFailureHandlerRestImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandlerImpl() {
        return new AccessDeniedHandlerRestImpl();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandlerImpl() {
        return new LogoutSuccessHandlerRestImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPointImpl() {
        return new AuthenticationEntryPointRestImpl();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(webExpressionVoter());
        return new AffirmativeBased(decisionVoters);
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter result = new WebExpressionVoter();
        result.setExpressionHandler(rbacWebSecurityExpressionHandler());
        return result;
    }

    @Bean
    public SecurityExpressionHandler rbacWebSecurityExpressionHandler() {
        return new RbacWebSecurityExpressionHandler();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider())
            .eraseCredentials(true)
            .userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configLogin(http);
        configAccess(http);
    }

    private void configLogin(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .successHandler(authenticationSuccessHandlerImpl())
            .failureHandler(authenticationFailureHandlerImpl())
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandlerImpl())
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()
            .rememberMe()
            .key("SSB#EF871D0AC3C5A2B7DAF6B4DC1E9D119E");
    }

    private void configAccess(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.authorizeRequests()
            .accessDecisionManager(accessDecisionManager())
            .antMatchers("/", "/static/**", "/login**", "/guest/**")
            .permitAll()
            .antMatchers("/api/shared/**")
            .hasRole("USER")
            .antMatchers("/api/_devops_/**")
            .hasRole("ADMIN")
            .antMatchers("/api/**")
            .access("passRbacCheck")
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPointImpl())
            .accessDeniedHandler(accessDeniedHandlerImpl());
    }

}
