# Introduction

The quick development boilerplate based on Spring (Boot) Framework which covers the general case of Java backend application
something like Account Module, Security Foundation, Audit System, Blog Upload/Download, Message Notification, Role Based Access Control etc.

We hope this boilerplate can help the users to focus on their business part when they know how this boilerplate is designed and implemented.
 
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
If we want the blob is upload and saved to the local storage ( your runtime server's file system ), 
just import another one replace it with

```
@Import({LocalStorageModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}
```





## Security

### Foundation

We add a classics security extension to Spring Security


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

