package info.developerblog.sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.client.ClientException;
import info.developerblog.sample.services.SimpleService;
import info.developerblog.spring.oneserver.client.OneHttpClient;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancerFactory;
import info.developerblog.spring.oneserver.server.HttpController;
import org.apache.commons.lang.StringUtils;

import one.nio.http.Param;
import one.nio.http.Path;
import one.nio.http.Request;
import one.nio.http.Response;

/**
 * @author alexander.tarasov
 */
@HttpController
public class SimpleController {
    @Autowired
    OneHttpClient oneHttpClient;

    @Autowired
    SimpleService simpleService;

    @Path("/simple")
    public Response getSimpleResponse(@Param("name") String name) {
        if (StringUtils.isBlank(name)) {
            return new Response(Response.INTERNAL_ERROR);
        }
        return Response.ok(simpleService.getSimpleAnswer(name));
    }

    @Path("/withBody")
    public Response postWithBody(Request request) {
        return Response.ok(request.getBody());
    }

    @Path("/loop")
    public Response loop() throws ClientException {
        OneHttpResponse response = oneHttpClient.call(
                "cool-app",
                OneHttpRequest.get("/simple?name=service")
        );

        if (response.isSuccess()) {
            return Response.ok(new byte[0]);
        }

        return new Response(Response.INTERNAL_ERROR);
    }
}
