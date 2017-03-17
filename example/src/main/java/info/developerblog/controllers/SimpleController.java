package info.developerblog.controllers;

import info.developerblog.spring.oneserver.HttpController;
import one.nio.http.Path;
import one.nio.http.Response;

/**
 * @author alexander.tarasov
 */
@HttpController({ "/test" })
public class SimpleController {
    @Path("/simple")
    public Response getSimpleResponse() {
        return Response.ok("Simple");
    }
}
