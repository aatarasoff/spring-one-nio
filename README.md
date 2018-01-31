# Spring Boot One NIO Starter

### This is very raw proof-of-concept. Don't use it in production!

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

### How it works

#### Server side

Create one or several controllers with `@HttpController` annotation:
```
@HttpController
public class SimpleController {
   //methods are here
}
```

In these class you should define methods to process http-requests:
```
    @Autowired
    SimpleService simpleService;

    @Path("/simple")
    public Response getSimpleResponse(@Param("name") String name) {
        return Response.ok(simpleService.getSimpleAnswer(name));
    }
```

As you see you could autowire any spring component, service or another bean into it.

In `application.yml` you could provide advertise ip and port as following:
```
oneserver:
  advertiseIp: 0.0.0.0
  port: 10080
```

#### Client side

This part still in progress and there is no very easy way to use it but it is possible.
Client side based on **ribbon** load balancer and `HttpClient` from `one-nio`.

For use it with ribbon you should define your client settings in `application.yml`:
```
cool-app:
  ribbon:
    listOfServers: 127.0.0.1:10080
```

They are all the same as in the case of using rest template or feign.

Then in your code you should autowire load balancer factory:
```
@Autowired
OneLoadBalancerFactory loadBalancerFactory;
```

and create load balancer for ribbon service, then call it:
```
OneHttpResponse response = loadBalancerFactory
                .create("cool-app")
                .executeWithLoadBalancer(
                        new OneHttpRequest(RequestMethod.GET, "http://cool-app/simple?name=service")
                );
```

See **example** project for more.

## Enjoy!
