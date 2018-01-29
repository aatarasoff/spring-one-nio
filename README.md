# Spring Boot One NIO Starter

## This is very raw proof-of-concept. Don't use it in production!

## This project helps with integration between [Spring Boot](https://projects.spring.io/spring-boot/) and [One NIO](https://github.com/odnoklassniki/one-nio).

### How to run

1. Checkout code from [one-nio](https://github.com/odnoklassniki/one-nio) repository to your local machine.
2. Install library to you local maven repository:

```
mvn install
```

3. Import project into your IDE, or run:

```
./gradlew example:bootRun
```

4. Call test endpoint with curl:

```
curl localhost:8080/test/simple
```

5. You should receive `200 OK` response with text body "Simple".

## Enjoy!
