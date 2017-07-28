# 介绍 - Introduction

基于Spring(Boot)的快速开发框架,覆盖了应用系统后端常用需求,
例如账户系统,安全设施,访问审计,文件或图片的上传下载,消息通知等.
我们希望达到的目标是使用者熟悉框架后,对代码进行微调或者调整后,
只需要关注业务,往里面添加业务模块,进行一些简单的配置,就可开发出满足业务需求的系统.
 
# 功能特征 - Features


## 安全 - Security

### 基础设施 - Foundation


### 认证策略 - Authentication 


### 授权策略 - Authorization


### 用户设备 - User Device


### 双因子 - Two Factor Support


## 多套账户示例 - Multi-Account Template


## 文件上传下载 - File Storage


## 访问审计 - Audit

### 登录审计 - Login


### 操作审计 - Operation


### 统计报表 - Report


## 消息通知 - Message

### 短信通知 - SMS


### 邮件通知 - Email


### 移动端通知 - Mobile


### 微信通知 - Wechat


# 模块化设计 - Modularization


# 使用指南 - Get Started
 
## 源码组织结构 - Source Code Inside



# 参数配置 - Configuration

## app

```
app.name=daas-appt
app.description=daas application template
```


## file storage - aliyun

```

in.clouthink.daas.sbb.attachment.alioss.keyId=
in.clouthink.daas.sbb.attachment.alioss.secret=
in.clouthink.daas.sbb.attachment.alioss.ossDomain=
in.clouthink.daas.sbb.attachment.alioss.imgDomain=
in.clouthink.daas.sbb.attachment.alioss.defaultBucket=
in.clouthink.daas.sbb.attachment.alioss.buckets.image=
```


## file storage - mongodb gridfs

```

```


## sms
```

in.clouthink.daas.sbb.sms.key=
in.clouthink.daas.sbb.sms.secret=
in.clouthink.daas.sbb.sms.templateId=
in.clouthink.daas.sbb.sms.registerTemplateId=
in.clouthink.daas.sbb.sms.forgetPasswordTemplateId=

```

## email
```
in.clouthink.daas.sbb.email.smtp.host=
in.clouthink.daas.sbb.email.smtp.port=
in.clouthink.daas.sbb.email.smtp.username=
in.clouthink.daas.sbb.email.stmp.password=
in.clouthink.daas.sbb.email.sender.from=
in.clouthink.daas.sbb.email.sender.fromAlias=
```


## jpush

```
in.clouthink.daas.edm.push.jpush.appKey=
in.clouthink.daas.edm.push.jpush.appSecret=
in.clouthink.daas.edm.push.jpush.maxRetries=
in.clouthink.daas.edm.push.jpush.timeToLive=
in.clouthink.daas.edm.push.jpush.apnsProduction=
```

## administrator user 

```
in.clouthink.daas.sbb.dashboard.administrator.email=
in.clouthink.daas.sbb.dashboard.administrator.username=
in.clouthink.daas.sbb.dashboard.administrator.cellphone=
in.clouthink.daas.sbb.dashboard.administrator.password=

```

## rbac

```
in.clouthink.daas.sbb.rbac.resourceFile=resources.json
```


 
# 附录 - 开发环境
 
## IDEA 导入 - how to import the project to IDEA IDE

> gradle cleanIdea
> gradle idea

## IDEA 调试 - how to debug in IDEA IDE

Create new debug configuration (type of gradle), and pop it with following value.

name | value
-----|-----
Gradle Project | in.clouthink.daas.sbb:dashboard
Tasks | clean bootRun
VM Options | <keep it empty>
Script parameters | -PjvmArgs="-Dspring.config.location=/var/appt/etc/dashboard/application.properties"

