# Introduction

The quick development boilerplate based on Spring (Boot) Framework which covers the general case of Java backend application
something like Account Module, Security Foundation, Audit System, File Upload/Download, Message Notification, Role Based Access Control etc.

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


### Authentication 


`TODO`


### Authorization
`TODO`


### User Device
`TODO`

### Two Factor Support
`TODO`


## Multi-Account Template
`TODO`


## File Storage
`TODO`


## Audit
`TODO`

### Login
`TODO`


### Operation
`TODO`


### Report
`TODO`


## Message
`TODO`


### SMS
`TODO`


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

