# Spring Boot One NIO Starter

## This is very raw proof-of-concept. Don't use it in production!

## This project helps with integration between [Spring Boot](https://projects.spring.io/spring-boot/) and [One NIO](https://github.com/odnoklassniki/one-nio).

### How to run

1. Import project into your IDE, or run:

```
./gradlew example:bootRun
```

2. Call test endpoint with curl:

```
curl localhost:10080/simple
```

3. You should receive `200 OK` response with text body "Simple".

## Enjoy!
