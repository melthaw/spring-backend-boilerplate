
# Introduction

The daas development libs based application template.
 
## Features
`TODO`

## Dependencies
`TODO`

# Get Started
 
## shared module

## account module

## audit module

## security module

## rbac module

## storage module

## attachment module

## event module

## message module

## dashapi module

## openapi module
 
# Appendix - how to import the project to IDEA IDE

> gradle cleanIdea
> gradle idea

# Appendix - how to debug in IDEA IDE

Create new debug configuration (type of gradle), and pop it with following value.

name | value
-----|-----
Gradle Project | in.clouthink.daas.sbb:dashboard
Tasks | clean bootRun
VM Options | <keep it empty>
Script parameters | -PjvmArgs="-Dspring.config.location=/var/appt/etc/dashboard/application.properties"


# Appendix - configuration

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
