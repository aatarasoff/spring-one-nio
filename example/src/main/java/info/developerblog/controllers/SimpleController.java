package info.developerblog.controllers;

import info.developerblog.spring.oneserver.server.HttpController;
import one.nio.http.Path;
import one.nio.http.Response;
import one.nio.http.VirtualHost;

/**
 * @author alexander.tarasov
 */
@HttpController
public class SimpleController {
    @Path("/simple")
    public Response getSimpleResponse() {
        return Response.ok("Simple");
    }
}
