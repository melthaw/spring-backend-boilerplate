package in.clouthink.daas.sbb.dashboard;

import in.clouthink.daas.sbb.security.impl.spring.UserDetailsAuthenticationProviderImpl;
import in.clouthink.daas.sbb.security.impl.spring.UserDetailsServiceImpl;
import in.clouthink.daas.sbb.security.impl.spring.mvc.AccessDeniedHandlerMvcImpl;
import in.clouthink.daas.sbb.security.impl.spring.mvc.AuthenticationEntryPointMvcImpl;
import in.clouthink.daas.sbb.security.impl.spring.mvc.AuthenticationFailureHandlerMvcImpl;
import in.clouthink.daas.sbb.security.impl.spring.mvc.AuthenticationSuccessHandlerMvcImpl;
import in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionHandler;
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

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class DashboardSecurityConfigurer extends WebSecurityConfigurerAdapter {

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
		AuthenticationSuccessHandlerMvcImpl result = new AuthenticationSuccessHandlerMvcImpl();
		result.setDefaultTargetUrl("/dashboard");
		result.setAlwaysUseDefaultTargetUrl(true);
		return result;
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandlerImpl() {
		return new AuthenticationFailureHandlerMvcImpl("/login?error=true");
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandlerImpl() {
		AccessDeniedHandlerMvcImpl accessDeniedHandler = new AccessDeniedHandlerMvcImpl();
		accessDeniedHandler.setErrorPage("/403");
		return accessDeniedHandler;
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPointImpl() {
		return new AuthenticationEntryPointMvcImpl("/login");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider())
			.eraseCredentials(true)
			.userDetailsService(userDetailsService());
	}

	//	public org.springframework.security.authentication.event.LoggerListener authenticationLoggerListener() {
	//		return new org.springframework.security.authentication.event.LoggerListener();
	//	}
	//
	//	@Bean
	//	public org.springframework.security.access.event.LoggerListener accessLoggerListener() {
	//		return new org.springframework.security.access.event.LoggerListener();
	//	}
	//	@Bean

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
		decisionVoters.add(new RoleVoter());
		//		decisionVoters.add(rbacVoter());
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
			//			.defaultSuccessUrl("/portal", true)
			.successHandler(authenticationSuccessHandlerImpl())
			//			.failureUrl("/login?error=true")
			.failureHandler(authenticationFailureHandlerImpl())
			.loginProcessingUrl("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			//.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll()
			.and()
			.rememberMe()
			.key("BBT#EF871D0AC3C5A2B7DAF6B4DC1E9D119E");
	}

	private void configAccess(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();

		http.authorizeRequests()
			.accessDecisionManager(accessDecisionManager())
			.antMatchers("/", "/40*", "/static/**", "/login**", "/guest/**", "/browsers**")
			.permitAll()
			.antMatchers("/dashboard**",
						 "/dashboard/**",
						 "/portal**",
						 "/portal/**",
						 "/api/my**",
						 "/api/my/**",
						 "/api/files**",
						 "/api/files/**",
						 "/api/attachments**",
						 "/api/attachments/**",
						 "/api/portal**",
						 "/api/portal/**",
						 "/api/messages**",
						 "/api/messages/**",
						 "/api/papers**",
						 "/api/papers/**",
						 "/api/contact**",
						 "/api/contact/**")
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
