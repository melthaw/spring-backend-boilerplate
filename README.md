# Introduction

The quick development boilerplate based on Spring (Boot) Framework which covers the general case of Java backend application
something like Account Module, Security Foundation, Audit System, File Upload/Download, Message Notification, Role Based Access Control etc.

And we also provide the CRUD sample to show how manage the item list , add new one item , update it or remove it.  

We hope this boilerplate can help the users to focus on their business part with this boilerplate, but first we will explain how it is designed and implemented.
 
# Features

## Modularization

First we design a modularized system (Thanks Spring and Gradle), 
our goal is simple add or remove one module without changing the Application , except the foundational modules.

For example, remove the message module in the boilerplate won't break the application, because the message is event-driven ,
The other modules dispatch event to the event bus, the event bus simply discard it if the message module not found. 

```
//Before
public class OpenApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{...,
		                                    SmsRestModuleConfiguration.class,
		                                    ..., 
		                                    OpenApiApplication.class}, args);
	}

}
```

Simply remove the module configuration from the startup script.

```
//After
public class OpenApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{...,
		                                    OpenApiApplication.class}, args);
	}

}

```



And we also force the module convention that's separating the abstraction and implementation, then your can switch from one 
implementation to another easily. 

For example, we provide more than one implementation module for the storage abstraction (:storage/core).

```
@Import({GridfsModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}

```
If you want upload the file and save it to the local storage ( your runtime server's file system ), 
just import another one and replace it as follow

```
@Import({LocalStorageModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}
```


## Security

### Foundation

Spring Security is a powerful security framework , it's easy to customize and extend . 
Based on the flexibility provided by Spring Security , we add more interesting feature to it like
multi-factor authentication, user device, audit and pluggable account system.


### Context 

Yes, Spring Security provides the context named `org.springframework.security.core.context.SecurityContext` and 
the corresponding helper class `org.springframework.security.core.context.SecurityContextHolder`. 

Why we need another one? 

```
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication == null) {
    throw some exception here?
}

```

Every time we retrieve the authentication , we must check the authentication , duplicated code everywhere.

What we supposed is that an exception is thrown automatically if no authentication found in the context,  
not check it and throw it by manual.

So we provide one new generic interface in module(:security/core)

```
public interface SecurityContext<T> {

	/**
	 * @return current user , or null if not authenticated
	 */
	T currentUser();

	/**
	 * @return the current user
	 * @throw AuthenticationRequiredException if not authenticated
	 */
	T requireUser();

}
```

And we provide one helper named `SecurityContexts` to load the implementation. Here is the sample: 

```
User user = (User)SecurityContexts.getContext().requireUser();
```

As you see, `requireUser()` is invoked which means if no authenticated user found in the context, it will throw one AuthenticationRequiredException.


The `SecurityContexts` requires that the implementation must follow the Java SPI  Spec and provide it in the file as follow

```
META-INF/services/in.clouthink.daas.sbb.security.SecurityContext
```

### Authentication & Authorization

First let's list the extension points what we implemented for Spring Security.

`User`

* org.springframework.security.authentication.AuthenticationProvider
    * org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
* org.springframework.security.core.userdetails.User
* org.springframework.security.core.userdetails.UserDetailsService

`Login & Logout`

* org.springframework.security.web.AuthenticationEntryPoint
* org.springframework.security.web.authentication.AuthenticationFailureHandler
* org.springframework.security.web.authentication.AuthenticationSuccessHandler
* org.springframework.security.web.authentication.logout.LogoutSuccessHandler

`Access`

* org.springframework.security.web.access.AccessDeniedHandler
    * org.springframework.security.web.access.AccessDeniedHandlerImpl
* org.springframework.security.access.expression.SecurityExpressionHandler
    * org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
* org.springframework.security.web.access.expression.WebSecurityExpressionRoot


The backend is designed as a restful service provider, 
so the security foundation must has the ability to process the restful request and response , 
not only the web page request and response.

And our implementations are listed as follow: 

`User`
* in.clouthink.daas.sbb.security.impl.spring.UserDetailsAuthenticationProviderImpl
* in.clouthink.daas.sbb.security.impl.spring.UserDetails
* in.clouthink.daas.sbb.security.impl.spring.UserDetailsServiceImpl

`Login & Logout`
* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationEntryPointRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationFailureHandlerRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationSuccessHandlerRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.LogoutSuccessHandlerRestImpl

`Access`
* in.clouthink.daas.sbb.security.impl.spring.rest.AccessDeniedHandlerRestImpl
* in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionHandler
* in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionRoot

#### How to configure Spring Security 

Please refer to `in.clouthink.daas.sbb.openapi.OpenApiSecurityConfigurer`. 

First , export the Spring Security extension points implementation. 

```

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

```

Then configure the authentication part
```

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
			.key("PLEASE_CHANGE_THIS");
	}

```

Finally configure the authorization part

```

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

```

### User Device
`TODO`

### Two Factor Support
`TODO`

## Multi-Account Template
`TODO`


## Audit

The [daas-audit](https://github.com/melthaw/spring-mvc-audit) is a simple and quick audit abstraction lib for spring mvc http request.
Please go https://github.com/melthaw/spring-mvc-audit to get more detail about it. Here we only explain what we extended and customized.  

`TODO`

## File Storage

The [daas-fss](https://github.com/melthaw/spring-file-storage-service) is APIs which make storing the blob file easy and simple.
Please go https://github.com/melthaw/spring-file-storage-service to get more detail about it. Here we only explain what we extended and customized.  

`TODO`

## Message

The [daas-edm](https://github.com/melthaw/spring-event-driven-message) is a lightweight event driven message framework based on reactor.
Please go https://github.com/melthaw/spring-event-driven-message to get more detail about it. Here we only explain what we extended and customized.


### SMS

In the boilerplate we choose the [aliyun SMS](https://www.aliyun.com/product/sms) as example .
It's simple and easy to integrate your SMS provider , just implement the following interface.

```
in.clouthink.daas.edm.sms.SmsSender
```

We also supply one dummy module(:message/sms/mock) which can be used in the development ENV. 

```
//for development
@Import({SmsAliyunModuleConfiguration.class})
//for production
@Import({DummySmsModuleConfiguration.class})
```

One more thing, the SMS history can be saved if you import the history module (:message/sms/history). 
By default we enable the SMS history in the boilerplate, if you don't like it, simple remove the import part to disable this feature.

```
@Import({SmsHistoryModuleConfiguration.class})
```


### Email
`TODO`


### Wechat
`TODO`


# Get Started
 `TODO`

## Source Code Inside
`TODO`



# `TODO`
Configuration


3 Ways to go
* application.properties
* zookeeper
* customized

 
# Appendix - Development ENV
 
## IDEA - how to import the project to IDEA IDE

```
> gradle cleanIdea
> gradle idea
```

## IDEA - how to debug in IDEA IDE

Create new debug configuration (type of gradle), and pop it with following value.

name | value
-----|-----
Gradle Project | in.clouthink.daas.sbb:openapiServer
Tasks | clean bootRun
VM Options | <keep it empty>
Script parameters | -PjvmArgs="-Dspring.config.location=/var/sbb/etc/securityServer/application.properties"

