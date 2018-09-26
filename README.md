# Spring Boot One NIO Starter

#### This project helps with integration between [Spring Boot](https://projects.spring.io/spring-boot/) and [One NIO](https://github.com/odnoklassniki/one-nio).

### How to connect the project

Add `jcenter` repository:
```groovy
repositories {
    jcenter()
}
```

or for maven:
```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com/</url>
    </repository>
</repositories>
```

And add dependency with latest version (or feel free to choose specific)
```groovy
compile 'info.developerblog.spring.cloud:spring-one-nio-starter:+'
```

or for maven:
```xml
<dependency>
    <groupId>info.developerblog.spring.cloud</groupId>
    <artifactId>spring-one-nio-starter</artifactId>
    <version>x.y.z</version>
</dependency>
```

### How to run

1. Import project into your IDE, or run:

```
./gradlew example:bootRun
```

2. Call test endpoint with curl:

```
curl localhost:10080simple?name=service
```

3. You should receive `200 OK` response with text body "Simple service".

### How it works

#### Server side

Create one or several controllers with `@HttpController` annotation:
```java
@HttpController
public class SimpleController {
   //methods are here
}
```

In this class you should define methods to process http-requests:
```java
@Autowired
SimpleService simpleService;

@Path("/simple")
public Response getSimpleResponse(@Param("name") String name) {
    return Response.ok(simpleService.getSimpleAnswer(name));
}
```

As you see you could autowire any spring component, service or another bean into it.

In `application.yml` you could provide advertise ip and port and other properties as following:
```yaml
oneserver:
  #required
  advertiseIp: 0.0.0.0
  port: 10080
  #optional
  selectorThreadsCount:        # default 0
  maxWorkersCount:             # default 0
  minWorkersCount:             # default 0
  queueTime:                   # default 0 
  keepAlive:                   # default 0
  threadPriority:              # default 5 or Thread.NORM_PRIORITY
  affinity:                    # default false
```

#### Client side

Client side based on **ribbon** load balancer and `HttpClient` from `one-nio`.

For use it with ribbon you should define your client settings in `application.yml`:
```yaml
cool-app:
  ribbon:
    listOfServers: 127.0.0.1:10080
    #supported client config keys
    ConnectTimeout: 1000
    ReadTimeout: 10000
    PoolMinThreads: 1
    PoolMaxThreads: 200
    KeepAlive: false
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

or with payload as object (one-nio byte serialization is used under the hood):
```java
Payload payload = new Payload().withValue("Hello!");

OneHttpResponse response = oneHttpClient.call(
        "cool-app",
        OneHttpRequest.post("/withBody").withPayload(payload)
);

Payload responsePayload = response.get();
process(responsePayload);
```

See **[example](https://github.com/aatarasoff/spring-one-nio/tree/master/example)** project for more.

## Enjoy!
