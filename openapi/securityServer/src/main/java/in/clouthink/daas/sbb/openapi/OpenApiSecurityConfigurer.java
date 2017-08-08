package in.clouthink.daas.sbb.openapi;

import in.clouthink.daas.sbb.security.impl.spring.UserDetailsAuthenticationProviderImpl;
import in.clouthink.daas.sbb.security.impl.spring.UserDetailsServiceImpl;
import in.clouthink.daas.sbb.security.impl.spring.rest.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


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
			.key("BBT#EF871D0AC3C5A2B7DAF6B4DC1E9D119E");
	}

	private void configAccess(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();

		http.authorizeRequests()
			.antMatchers("/", "/40*", "/static/**", "/login**", "/api/guest/**", "/api/dict/**")
			.permitAll()
			.antMatchers("/api/**")
			.hasRole("USER")
			.antMatchers("/api/_devops_/**")
			.hasRole("ADMIN")
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPointImpl())
			.accessDeniedHandler(accessDeniedHandlerImpl());
	}

}
