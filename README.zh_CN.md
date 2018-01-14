# 概述

本项目基于Spring技术栈, 提供了一套快速的JAVA后端应用的开发模板, 我们区别与其他的开发模板的特点是:

* 高度模块化
* 可扩展的插件机制
* Spring最佳实践
* 不夹带私货（部分代码闭源并收费）

目前我们覆盖Java后端应用的最基础和最常见的功能:

* 身份管理
* 审计系统
* 安全设施
* 文件管理
* 消息通知
* 基于角色的权限管理

同时我们提供了部分示例,展现如何快速的增加业务模块,特别是最常见的增删改查操作.

我们的定位是提供基于Spring (Boot)框架的快速开发模板和最佳实践, 让用户关注核心业务, 而不是纠结于搭建开发环境.
当然, 使用者需要掌握本框架的设计思路和实现方式. 接下来, 我们会逐一进行解释.

[User Guide - English Version](./README.md)

# 快速上手

> 首先请提前在自己开发机上准备开发环境, 包括 Java 8, Gradle 2.x 和 Mongodb Server等.

## 代码构建

目前只支持gradle

```
gradle clean build
```

## 启动 - docker

* 记得先用gradle进行代码构建
* 确保的你的开发环境已经安装了docker和docker comose

```
cd openapi
docker-compose build
docker-compose up -d
```

## 启动 - 传统方式

下面我们介绍怎么用传统的方式启动服务

### 启动API服务器

API服务器负责将业务服务通过REST API的形式发布出来, 首先我们演示一下如何启动API服务器.

在此我们提供了启动API服务器需要的一个最小配置的`application.properties`（该配置文件是Spring Boot启动时需要的）
  
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

将`application.properties`放在任意可访问的目录,然后按照下面的方式启动,记得将`spring.config.location`的值替换为`application.properties`全路径名.

```sh
> cd openapi/server
> gradle clean bootRun  -PjvmArgs="-Dspring.config.location=the_full_path_of_the_application.properties"
```

### 启动API文档服务器

我们使用swagger2来生成API说明文档,下面是启动API文档服务器的最小配置`application.properties`

```ini
app.name=spring-backend-boilerplate-api-doc
app.description=spring backend boilerplate api doc

server.port=8082
server.address=127.0.0.1
server.session-timeout=360000
```

启动方式和API服务器类似,请按照相同的思路来操作:

```sh
> cd openapi/doc
> gradle clean bootRun  -PjvmArgs="-Dspring.config.location=the_full_path_of_the_application.properties"
```

当API文档服务器启动完成后,打开浏览器,访问以下地址即可:

```
http://127.0.0.1:8082/swagger-ui.html
```

# 功能特征

## 模块化

Spring Boot和Gradle的功能已经非常强,在这个基础上,我们去设计一个完全模块化的系统就变得很容易了.
我们的目标是不需要修改业务代码的情况下, 可以非常轻松的添加或者移除一个模块（当然被整个框架依赖的基础设施模块除外）.

要达到这个目标,需要依赖以下技术:

* Spring Boot Starter (这是自动化注入模块和启动模块的解决方案)
* Gradle Build（我们需要根据Gradle的使用规范,设计一个多模块的Gradle工程）

Spring Boot Starter是最关键的, 因为除了Gradle, 我们还可以选择Maven作为构建工具.

* 为每个模块提供一个自动配置的文件 （`@Configuration`）
* 告诉Spring Boot 启动我们定义的自动配置文件 （`META-INF/spring.factories`）

目前我们提供了以下快速启动模块

* :account/starter
* :audit/starter
* :menu/starter
* :rbac/starter
* :sample/news/starter
* :sample/attachment/starter
* :sample/setting/starter
* :message/sms/starter
* :storage/starter

### 代码示例

我们以本项目中的消息模块 (位于`$RROJECT_ROOT/message/sms`目录下) 为例:

Spring的配置文件如下:

```
package in.clouthink.daas.sbb.sms;

@Configuration
@Import({MockSmsModuleConfiguration.class, SmsHistoryModuleConfiguration.class})
public class DummySmsRestModuleConfiguration {

}
```

对应的`starter/src/main/resources/META-INF/spring.factories`文件内容如下:

```ini
#message/sms/starter/src/main/resources/META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
in.clouthink.daas.sbb.sms.DummySmsRestModuleConfiguration
```

接下来我们演示一下怎么样实现动态添加和移除该模块:

### 最佳实践

我们前面提到了我们的目标是不修改业务代码的情况下可以做到快速的添加或者移除一个模块, 做法很简单, 在gradle的build文件里面添加或者拿掉业务模块对应的starter就可以了.

1.添加新闻管理(news)模块

> 不需要修改业务代码,添加该模块后,新闻管理模块自动注册到整个应用,API Server启动后, 自动发布和新闻管理有关的所有REST API, 菜单中自动出现新闻管理的菜单项.

```gradle
//openapi/server/build.gradle
dependencies {

    ...
    compile project(':sample/news/starter')
    ...
}

```

2.移除新闻管理(news)模块

> 不需要修改业务代码,移除该模块后,API Server启动后, 新闻管理有关的所有REST API下架, 菜单中不会出现新闻管理的菜单项

```gradle
//openapi/server/build.gradle
dependencies {

    ...
    //after
    //compile project(':sample/news/starter')
    ...
}
```


### 手工注入（不采用spring boot starter）

Spring Boot Starter非常强大, 但是对于那些不熟悉或者不喜欢Spring Boot Starter的使用者, 我们也支持编程式的方式来增加新的业务模块.

首先我们强制分离模块的接口和实现, 这样才可能在不同的实现版本之间轻松切换. 这是模块化的最佳实践.

以本项目提供的文件存储服务(接口定义在`:storage/core`)为例, 我们提供了好几个版本的实现:

* 本地文件系统
* Mongodb的Gridfs系统
* 阿里云存储

下面这段代码表示采用Mongodb提供的Gridfs系统来进行存储

```java
@Import({GridfsModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}

```

如果你只需要把文件存储在本地（ 和你的服务器放在一起 ）, 只需要注入一个实现了本地文件存储的模块即可, 示例如下:

```java
@Import({LocalStorageModuleConfiguration.class})
public class StorageRestModuleConfiguration {

}
```


## 安全

### 基础

`Spring Security`是一个功能全面而且非常强大的安全框架, 在设计上充分考虑了定制化和扩展性.
基于`Spring Security`提供的弹性设计, 我们在此基础上增加了一些非常有趣的特征:

* 用户管理
* 设备管理
* 多因子认证
* 审计系统
* 可插拔的账户系统

### 安全上下文

`Spring Security`通过 `org.springframework.security.core.context.SecurityContext` 和 `org.springframework.security.core.context.SecurityContextHolder`
为使用者提供安全上下文（也就是获取当前的认证信息）.

**为什么我们又重复发明轮子 - 重新设计了一个新的安全上下文?**

首先我们看看一个常见的代码:

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//now check the authentication
if (authentication == null) {
    throw some exception here?
}

```

如果基于`Spring Security`提供的上下文, 我们每次获取了认证信息后, 还需要去检查认证信息 , 基本上是一次编写, 四处拷贝粘贴.

我们希望的是, 如果获取的上下文不满足我们的要求, 自动抛出异常 , 而不是每次都要手工编写代码去判断.

我们对这个需求抽象成代码(该代码在`:security/core`模块中), 如下:


```java
package in.clouthink.daas.sbb.security;

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

这个接口定义了两个方法

方法 | 描述
---|---
currentUser() | 保留了对Spring Security Context的支持
requireUser() | 这个方法非常有趣, 表示在应用里面我需要一个用户, 它的返回结果和`currentUser()`完全一样, 但是如果当前上下文没有已认证的用户,就会抛出一个`AuthenticationRequiredException`异常

下面我们给出一段使用示例代码:

```java
User user = (User)SecurityContexts.getContext().requireUser();
```

`SecurityContexts` 是一个抽象接口, 只要遵循Java SPI规范, 就可以很轻松提供用户自己的实现（当然, 我们提供了一套默认实现）

> META-INF/services/in.clouthink.daas.sbb.security.SecurityContext

内容如下:

```
#spring-backend-boilerplate/security/spring/src/main/resources/META-INF/services/in.clouthink.daas.sbb.security.SecurityContext
in.clouthink.daas.sbb.security.impl.spring.SecurityContextImpl
```


### 认证与授权

首先,我们列出我们扩展的`Spring Security`的清单:

**User - 用户**

* org.springframework.security.authentication.AuthenticationProvider
    * org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
* org.springframework.security.core.userdetails.User
* org.springframework.security.core.userdetails.UserDetailsService

**Login & Logout - 登录和登出**

* org.springframework.security.web.AuthenticationEntryPoint
* org.springframework.security.web.authentication.AuthenticationFailureHandler
* org.springframework.security.web.authentication.AuthenticationSuccessHandler
* org.springframework.security.web.authentication.logout.LogoutSuccessHandler

**Access Control - 访问控制**

* org.springframework.security.web.access.AccessDeniedHandler
    * org.springframework.security.web.access.AccessDeniedHandlerImpl
* org.springframework.security.access.expression.SecurityExpressionHandler
    * org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
* org.springframework.security.web.access.expression.WebSecurityExpressionRoot

我们对后端的定义是作为REST服务提供者. 因此安全基础设置必须保护所有的REST请求, 而不是传统的控制到页面菜单,按钮一级.

对于前面提到的`Spring Security`扩展, 我们提供的实现如下:

**User - 用户**

* in.clouthink.daas.sbb.security.impl.spring.UserDetailsAuthenticationProviderImpl
* in.clouthink.daas.sbb.security.impl.spring.UserDetails
* in.clouthink.daas.sbb.security.impl.spring.UserDetailsServiceImpl

**Login & Logout - 登录和登出**

* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationEntryPointRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationFailureHandlerRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.AuthenticationSuccessHandlerRestImpl
* in.clouthink.daas.sbb.security.impl.spring.rest.LogoutSuccessHandlerRestImpl

**Access Control - 访问控制**

* in.clouthink.daas.sbb.security.impl.spring.rest.AccessDeniedHandlerRestImpl
* in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionHandler
* in.clouthink.daas.sbb.rbac.impl.spring.security.RbacWebSecurityExpressionRoot

> 即使你不需要这么完整的扩展, 这里面的实现方式应该也具有一定的参考价值.

#### 配置`Spring Security`

详情请参考实现 `in.clouthink.daas.sbb.openapi.OpenApiSecurityConfigurer`.

首先, 我们需要导出我们的`Spring Security`实现（ 以Spring Bean的形式 ）.

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

接下来在`Spring Security`中集成这些扩展实现.

认证示例:

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

授权示例:

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


## 审计

[daas-audit](https://github.com/melthaw/spring-mvc-audit) 是一个简单易用的审计库, 专门对Spring Mvc的HTTP请求进行审计.
我们在本项目中集成了该审计库, 关于如何集成, 请查看 https://github.com/melthaw/spring-mvc-audit , 里面有详细的使用说明.


在本项目中, `:audit/impl`模块完整实现了[daas-audit](https://github.com/melthaw/spring-mvc-audit) 的`AuditEvent` 审计API:

* in.clouthink.daas.audit.core.MutableAuditEvent
    * in.clouthink.daas.sbb.audit.domain.model.AuditEvent
* in.clouthink.daas.audit.spi.AuditEventPersister
    * in.clouthink.daas.sbb.audit.spiImpl.AuditEventPersisterImpl

[daas-audit](https://github.com/melthaw/spring-mvc-audit) 并不支持对用户登录和登出历史的审计, 我们也进行了扩展.

* in.clouthink.daas.sbb.audit.domain.model.AuthEvent
* in.clouthink.daas.sbb.audit.service.AuthEventService

下面的代码示例演示了如何启用审计功能 （使用`@EnableAudit`注解）:

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
        ...
    }

}
```

## 文件存储

[daas-fss](https://github.com/melthaw/spring-file-storage-service) 是一个简单易用的文件存储库 ,
我们在本项目中集成了文件存储功能, 关于如何集成, 请查看 https://github.com/melthaw/spring-file-storage-service , 里面有详细的使用说明.

接下来我们解释一下我们基于[daas-fss](https://github.com/melthaw/spring-file-storage-service)所做的扩展和定制.

目前我们提供了三种文件存储实现:

* aliyun oss (:storage/alioss)
* mongodb gridfs (:storage/gridfs)
* local file system (:storage/localfs)

> 不同的文件存储服务依赖于不同的底层服务, 因此最终导致的下载链接也不一样, 这个可能对你的业务系统造成一定影响, 例如你刚开始选择了一种实现 , 后来又切换到另外一种实现 , 就需要进行数据迁移.

下面是我们提供的下载链接的抽象接口

* in.clouthink.daas.sbb.storage.spi.DownloadUrlProvider

例如: 如果你选择使用`:storage/localfs` 本地文件系统来存储文件, 对应的下载地址如下:

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

下面是如何启用文件存储模块（在前面的模块化章节有更详细的描述）

```java
@Import(StorageModuleConfiguration.class)
public class SpringBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ...
    }
}
```

## 基于事件的消息

[daas-edm](https://github.com/melthaw/spring-event-driven-message) 是一个基于spring reactor实现的事件驱动消息的框架 ,
我们在本项目中集成了该框架, 关于如何集成, 请查看 https://github.com/melthaw/spring-event-driven-message , 里面有详细的使用说明.

### 短信通知

> 在本模板中我们用[阿里云短消息](https://www.aliyun.com/product/sms) 作为示例.

我们对短信通知进行抽象, 只需要实现下面这个接口就可以集成你的短信通知服务.


```
in.clouthink.daas.edm.sms.SmsSender
```

我们也提供了一个模拟实现(`:message/sms/mock`) , 该实现可以用于开发环境, 接受所有的短信发送请求, 只是简单的在console打印出已发送, 这样可以避免在开发过程中消耗你的实际流量.

```java
//for development
@Import({SmsAliyunModuleConfiguration.class})
//for production
@Import({DummySmsModuleConfiguration.class})
```

如果你导入短消息发送历史模块(`:message/sms/history`), 所有的短消息发送记录将会记录在案.
默认情况下, 我们启用了短消息历史纪录功能, 如果你不需要, 注释掉或者删除对应的`Import`代码即可.


```java
@Configuration
@Import({SmsHistoryModuleConfiguration.class})
public class SmsRestModuleConfiguration {

}

```

下面是手工启用短消息服务模块, 我们推荐的方式是使用`Spring Boot Starter`, 请参考模块化章节.

```java
@Import(SmsRestModuleConfiguration.class)
public class SpringBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) { 
        ...
    }
}
```

# 附录 - 配置示例


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

## resource - 资源

在RBAC（基于角色的访问控制）中, 我们把要保护的对象叫做resource（资源）, resource可能（但是不限于）包括以下类型:

* REST服务
* 菜单
* 按钮
* 某个页面

为此我们设计了一个资源服务提供者的API :

* in.clouthink.daas.sbb.rbac.spi.ResourceProvider

所有的ResourceProvider（资源服务提供者）最终需要以`Spring Bean`的形式暴露给框架（和我们常用的Component, Service, Controller类似）.
当应用启动的时候会自动扫描并注册这些resource bean.

最常见的资源莫过于菜单, 在大量的应用中几乎都会包括一个菜单授权功能, 一般是将菜单授权给某个角色或者某个用户组.
我们设计了一个插拔式的菜单模块, 并提供了一整套注解（annotation) , 有了这套注解, 定义菜单和扩展菜单就很轻松了.

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

# 示例 - 快速开发业务模块

`TODO`
 
# 附录 - 开发环境
 
## IDEA - 如何导入

```sh
> gradle cleanIdea
> gradle idea
```

## IDEA - 怎么调试

新增类型为Gradle的调试配置, 对应的值如下:

配置项 | 值
-----|-----
Gradle Project | spring-backend-boilerplate:openapi:server
Tasks | clean bootRun
VM Options | 保留为空
Script parameters | -PjvmArgs="-Dspring.config.location=/var/sbb/etc/openapi/application.properties"

