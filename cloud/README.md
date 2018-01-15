# quick start

## convention

> In the demonstration,  gateway, eureka server and service provider are all running on same machine.

Please change your configuration to match your devops environment.

## prepare

1. run `ifconfig` to get your ip address.

```
ifconfig
```

2. and update `.env` file.

```
DEVOPS_HOST=192.168.31.42
EUREKA_SERVICE_URL=http://${DEVOPS_HOST}:8761/eureka/
MONGODB_HOST=mongodb
MONGODB_PORT=27017
REDIS_HOST=redis
REDIS_PORT=6379
```

## how

1. build

> build all if it's the first time to start it up.

```
gradle clean build
docker-compose build
```

2. start

```
docker-compose up -d
docker-compose ps
```

3. stop

```
docker-compose down
```

## what

> please make sure the eureka registry is started up (see [spring cloud boilerplate](https://github.com/melthaw/spring-cloud-boilerplate) )

4 nodes are created

* openapi server 1
* openapi server 2
* mongodb server
* redis server

> openapi server 1 & 2 shared the session by redis server


## Refz

* [spring session 1.3.1](https://docs.spring.io/spring-session/docs/1.3.1.RELEASE/reference/html5/)