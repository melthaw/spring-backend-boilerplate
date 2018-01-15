# Overview

The quick development boilerplate based on Spring (Boot) Framework which covers the general case of Java backend application
something like Account Module, Security Foundation, Audit System, File Upload/Download, Message Notification, Role Based Access Control etc.

And we also provide the CRUD sample to show how manage the item list , add new one item , update it or remove it.  

We hope this boilerplate can help the users to focus on their business part . Before that ,  we will explain how it is designed and implemented.

* [用户使用指南-中文版](./README.zh_CN.md)

# Get Started

> Please make sure the Java 8, Gradle 2.x and Mongodb are installed on your development machine.

We support Spring Cloud now, here is the [User Guide - Spring Cloud Support](./cloud/README.md)

## Build

Build using `Gradle`

```
gradle clean build
```

## Start up - by Docker

```
cd openapi
docker-compose build
docker-compose up -d
```

## Start up - by Manual

### Start API Server
  
And here is the minimized application.properties to start the boilerplate.
  
```ini
app.name=spring-backend-boilerplate
app.description=spring-backend-boilerplate

in.clouthink.daas.sbb.account.password.salt=@account.sbb.daas.clouthink.in
in.clouthink.daas.sbb.account.administrator.email=changeit@example.com
in.clouthink.daas.sbb.account.administrator.username=administrator
in.clouthink.daas.sbb.account.administrator.cellphone=13000000000
in.clouthink.daas.sbb.account.administrator.password=Please_change_the_pwd

in.clouthink.daas.sbb.setting.system.name=spring-backend-boilerplate
in.clouthink.daas.sbb.setting.system.contactEmail=support-team@example.com
in.clouthink.daas.sbb.setting.system.contactPhone=13000000000

logging.file=/var/sbb/log/server.log
logging.level.*=INFO
logging.level.in.clouthink.daas=DEBUG

server.port=8081
server.address=127.0.0.1
server.session-timeout=360000 

spring.mvc.date-format=yyyy-MM-dd
spring.mvc.favicon.enabled=false

multipart.enabled=true
multipart.max-file-size=20Mb
multipart.max-request-size=20Mb

spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true

spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ss.sss'Z'

spring.data.mongodb.uri=mongodb://localhost:27017/spring-backend-boilerplate

```  

Then we can start it with

```sh
> cd openapi/server
> gradle clean bootRun  -PjvmArgs="-Dspring.config.location=the_full_path_of_the_application.properties"
```

## Start ApiDoc Server

And here is the minimized application.properties to start the boilerplate. 

```ini
app.name=spring-backend-boilerplate-api-doc
app.description=spring backend boilerplate api doc

server.port=8082
server.address=127.0.0.1
server.session-timeout=360000
```

Then we can start it with

```sh
> cd openapi/doc
> gradle clean bootRun  -PjvmArgs="-Dspring.config.location=the_full_path_of_the_application.properties"
```

After the swagger doc server booted, open browser and visit the api doc at

```
http://127.0.0.1:8082/swagger-ui.html
```

# Features

## Modularization

First we design a modularized system (Thanks Spring Boot and Gradle),
our goal is to simple add or remove one module without changing the Application , except the foundational modules.

All these come to reality are belongs to the features of Spring Boot Starter.

* Provide the auto configuration for each module.
* Tell spring how to enable the auto configuration.

### Example

Message module for example

Auto Configuration

```
@Configuration
@Import({MockSmsModuleConfiguration.class, SmsHistoryModuleConfiguration.class})
public class DummySmsRestModuleConfiguration {

}
```

Starter by spring.factories

```ini
#message/sms/starter/src/main/resources/META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
in.clouthink.daas.sbb.sms.DummySmsRestModuleConfiguration
```

### Practise

Remove the message module in the boilerplate won't break the application, because the message is event-driven ,
The other modules dispatch event to the event bus, the event bus simply discard it if the message module not found. 

```gradle
//openapi/server/build.gradle
dependencies {

    ...
    compile project(':sample/setting/starter')

    compile project(':message/sms/starter')

    compile project(':storage/starter')
    ...
}

```

Simply remove the module configuration from the startup script.

```gradle
//openapi/server/build.gradle
dependencies {

    ...
    compile project(':sample/setting/starter')

    //after
    //compile project(':message/sms/starter')

    compile project(':storage/starter')
    ...
}
```

### Without Starter

And we also force the module convention that's separating the abstraction and implementation, then your can switch from one 
implementation to another easily. 

For example, we provide more than one implementation module for the storage abstraction (:storage/core).

```java
@Import({GridfsModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}

```
If you want upload the file and save it to the local storage ( your runtime server's file system ), 
just import another one and replace it as follow

```java
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

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication == null) {
    throw some exception here?
}

```

Every time we retrieve the authentication , we must check the authentication , duplicated code everywhere.

What we supposed is that an exception is thrown automatically if no authentication found in the context,  
not check it and throw it by manual.

So we provide one new generic interface in module(:security/core)

```java
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

```java
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

```java

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

```java
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

```java
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


## Audit

The [daas-audit](https://github.com/melthaw/spring-mvc-audit) is a simple and quick audit abstraction lib for spring mvc http request.
Please go https://github.com/melthaw/spring-mvc-audit to get more detail about it. Here we only explain what we extended and customized.  

The module(:audit/impl) implements the daas-audit's AuditEvent SPI including:
 
* in.clouthink.daas.audit.core.MutableAuditEvent
    * in.clouthink.daas.sbb.audit.domain.model.AuditEvent
* in.clouthink.daas.audit.spi.AuditEventPersister
    * in.clouthink.daas.sbb.audit.spiImpl.AuditEventPersisterImpl

And the login history is supported which is not covered in daas-audit.

* in.clouthink.daas.sbb.audit.domain.model.AuthEvent
* in.clouthink.daas.sbb.audit.service.AuthEventService

Here is the configuration to enable the audit module

```java
@EnableAudit
public class SpringBootApplication extends SpringBootServletInitializer {

	@Bean
	public AuditEventPersister auditEventPersisterImpl() {
		return new AuditEventPersisterImpl();
	}

	@Bean
	public AuditConfigurer auditConfigurer() {
		return result -> {
			result.setSecurityContext(new SecurityContextAuditImpl());
			result.setAuditEventPersister(auditEventPersisterImpl());
			result.setErrorDetailRequired(true);
		};
	}

    public static void main(String[] args) { 
        AuditRestModuleConfiguration.class,
        ...
        SpringBootApplication.class
    }
}
```

## File Storage

The [daas-fss](https://github.com/melthaw/spring-file-storage-service) is APIs which make storing the blob file easy and simple.
Please go https://github.com/melthaw/spring-file-storage-service to get more detail about it. Here we only explain what we extended and customized.  

Now we provide three file storage implementation 
* aliyun oss (:storage/alioss)
* mongodb gridfs (:storage/gridfs)
* local file system (:storage/localfs)

Because different storage service stores the file in different system , the download url goes to different as well.

Here is the abstraction we supplied to extend.

* in.clouthink.daas.sbb.storage.spi.DownloadUrlProvider

For example, if you choose the :storage/localfs , the download url goes to:

```java
public class LocalfsDownloadUrlProvider implements DownloadUrlProvider {

	@Autowired
	private LocalfsConfigureProperties localfsConfigureProperties;

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public String getDownloadUrl(String id) {
		FileObject fileObject = fileObjectService.findById(id);
		if (fileObject == null) {
			throw new FileNotFoundException(id);
		}

		return localfsConfigureProperties.getDowloadUrlPrefix() + fileObject.getFinalFilename();
	}

}

```

Here is the configuration to enable the `Storage` module

```java
public class SpringBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) { 
        StorageModuleConfiguration.class,
        ...
        SpringBootApplication.class
    }
}
```

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

```java
//for development
@Import({SmsAliyunModuleConfiguration.class})
//for production
@Import({DummySmsModuleConfiguration.class})
```

One more thing, the SMS history can be saved if you import the history module (:message/sms/history). 
By default we enable the SMS history in the boilerplate, if you don't like it, simple remove the import part to disable this feature.

```java
@Configuration
@Import({SmsHistoryModuleConfiguration.class})
public class SmsRestModuleConfiguration {

}

```

Here is the configuration to enable the `Message` module

```java
public class SpringBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) { 
        SmsRestModuleConfiguration.class,
        ...
        SpringBootApplication.class
    }
}
```

# Configuration


## application.properties

### account

```ini
in.clouthink.daas.sbb.account.password.salt=
in.clouthink.daas.sbb.account.administrator.email=
in.clouthink.daas.sbb.account.administrator.username=
in.clouthink.daas.sbb.account.administrator.cellphone=
in.clouthink.daas.sbb.account.administrator.password=
```
### storage

```ini
#alioss
in.clouthink.daas.sbb.storage.alioss.keyId=
in.clouthink.daas.sbb.storage.alioss.secret=
in.clouthink.daas.sbb.storage.alioss.ossDomain=
in.clouthink.daas.sbb.storage.alioss.imgDomain=
in.clouthink.daas.sbb.storage.alioss.defaultBucket=
in.clouthink.daas.sbb.storage.alioss.buckets.key1=
in.clouthink.daas.sbb.storage.alioss.buckets.key2=
```

### sms

```ini
in.clouthink.daas.sbb.sms.aliyun.area=
in.clouthink.daas.sbb.sms.aliyun.accessKey=
in.clouthink.daas.sbb.sms.aliyun.accessSecret=
in.clouthink.daas.sbb.sms.aliyun.signature=
in.clouthink.daas.sbb.sms.aliyun.smsEndpoint=
in.clouthink.daas.sbb.sms.aliyun.templateId=
```


### setting

```ini
in.clouthink.daas.sbb.setting.system.name=
in.clouthink.daas.sbb.setting.system.contactEmail=
in.clouthink.daas.sbb.setting.system.contactPhone=
```

## resource 

The resource is the term we called in RBAC, which is protected by authorization system. 
The resource should be a rest endpoint,or the visible menu item in the GUI , even a Create button in a page.
 
We design a resource registry SPI as 

* in.clouthink.daas.sbb.rbac.spi.ResourceProvider

All the resource provider implementation only required to implement it as a spring bean. 
The boilerplate scans and discovers it , and register the resources automatically.

The classic resource is the menu and action which are granted to the role and accessed control by role permission.
We design the pluggable menu & action module , and supply the annotation to make it easy to use.

Here is the example

```java
@EnableMenu(pluginId = "plugin:menu:sample",
			extensionPointId = Menus.ROOT_EXTENSION_POINT_ID,
			menu = {@Menu(virtual = true,
						  code = "menu:dashboard:sample",
						  name = "sample",
						  order = 100,
						  metadata = {@Metadata(key = "icon", value = "fa fa-gear")},
						  extensionPoint = {@ExtensionPoint(id = "extension:menu:sample")}),

					@Menu(virtual = true,
						  code = "menu:dashboard:system",
						  name = "system",
						  order = 200,
						  metadata = {@Metadata(key = "icon", value = "fa fa-gear")},
						  extensionPoint = {@ExtensionPoint(id = "extension:menu:system")}),

			})
```

More detail about the usage please check out the description of the Java file.

# Sample - new business module 

`TODO`
 
# Appendix - Development ENV
 
## IDEA - how to import the project to IDEA IDE

```sh
> gradle cleanIdea
> gradle idea
```

## IDEA - how to debug in IDEA IDE

Create new debug configuration (type of gradle), and pop it with following value.

name | value
-----|-----
Gradle Project | spring-backend-boilerplate:openapi:server
Tasks | clean bootRun
VM Options | <keep it empty>
Script parameters | -PjvmArgs="-Dspring.config.location=/var/sbb/etc/openapi/application.properties"

