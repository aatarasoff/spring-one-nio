# Spring Boot One NIO Starter

#### This project helps with integration between [Spring Boot](https://projects.spring.io/spring-boot/) and [One NIO](https://github.com/odnoklassniki/one-nio).

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

### How it works

#### Server side

Create one or several controllers with `@HttpController` annotation:
```java
@HttpController
public class SimpleController {
   //methods are here
}
```

In these class you should define methods to process http-requests:
```java
@Autowired
SimpleService simpleService;

@Path("/simple")
public Response getSimpleResponse(@Param("name") String name) {
    return Response.ok(simpleService.getSimpleAnswer(name));
}
```

As you see you could autowire any spring component, service or another bean into it.

In `application.yml` you could provide advertise ip and port as following:
```yaml
oneserver:
  advertiseIp: 0.0.0.0
  port: 10080
```

#### Client side

This part still in progress and there is no very easy way to use it but it is possible.
Client side based on **ribbon** load balancer and `HttpClient` from `one-nio`.

For use it with ribbon you should define your client settings in `application.yml`:
```yaml
cool-app:
  ribbon:
    listOfServers: 127.0.0.1:10080
```

They are all the same as in the case of using rest template or feign.

Then in your code you should autowire specific http client:
```java
@Autowired
OneHttpClient httpClient;
```

and create request, then call it:
```java
OneHttpResponse response = httpClient.call(
	"cool-app",
	OneHttpRequest.get("/loop")
);

//do something with response
check(response.isSuccess());
```

or with body:
```java
byte[] payload = "Hello!".getBytes(StandardCharsets.UTF_8);

OneHttpResponse response = httpClient.call(
        "cool-app",
        OneHttpRequest.post("/withBody").withBody(payload)
);

process((byte[]) response.getPayload());
```

See **[example](https://github.com/aatarasoff/spring-one-nio/tree/master/example)** project for more.

## Enjoy!
