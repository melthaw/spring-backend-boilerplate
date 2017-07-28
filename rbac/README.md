# guide


* gradle


```
compile project(':passcode/impl')
```

* spring boot - java startup

```
    public static void main(String[] args) {
		SpringApplication.run(new Object[]{AccountModuleConfiguration.class,
										   RepositoryModuleConfiguration.class,
										   ServiceModuleConfiguration.class,
										   EventModuleConfiguration.class,
										   RbacModuleConfiguration.class,
										   AuditModuleConfiguration.class,
										   GridfsModuleConfiguration.class,
										   AttachmentModuleConfiguration.class,
										   DashApiModuleConfiguration.class,
										   SecurityBackendModuleConfiguration.class,
										   DashboardApplication.class}, args);
	}


```

* spring boot - configuration

add to your `application.properties`

```
in.clouthink.daas.sbb.account=

```
