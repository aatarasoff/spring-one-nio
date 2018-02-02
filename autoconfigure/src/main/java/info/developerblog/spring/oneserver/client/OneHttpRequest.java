package info.developerblog.spring.oneserver.client;

import java.net.URI;

import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.client.ClientRequest;
import org.apache.commons.lang.StringUtils;

import one.nio.http.Request;

/**
 * @author alexander.tarasov
 */
public class OneHttpRequest extends ClientRequest implements Cloneable {
    private final Request request;

    public OneHttpRequest(RequestMethod requestMethod, String uri) {
        setUri(URI.create(uri));
        this.request = toRequest(
                new Request(toOneRequestMethod(requestMethod), "", true)
        );
    }

    public OneHttpRequest withBody(byte[] body) {
        this.request.setBody(body);
        return this;
    }

    public static OneHttpRequest get(String uri) {
        return new OneHttpRequest(RequestMethod.GET, uri);
    }

    public static OneHttpRequest post(String uri) {
        return new OneHttpRequest(RequestMethod.POST, uri);
    }

    public static OneHttpRequest put(String uri) {
        return new OneHttpRequest(RequestMethod.PUT, uri);
    }

    public static OneHttpRequest patch(String uri) {
        return new OneHttpRequest(RequestMethod.PATCH, uri);
    }

    public static OneHttpRequest delete(String uri) {
        return new OneHttpRequest(RequestMethod.DELETE, uri);
    }

    private Request toRequest(Request request) {
        StringBuilder pathBuilder = new StringBuilder();

        pathBuilder.append(getUri().getPath());

        if (StringUtils.isNotBlank(getUri().getQuery())) {
            pathBuilder
                    .append("?")
                    .append(getUri().getQuery());
        }

        Request result = new Request(
                request.getMethod(),
                pathBuilder.toString(),
                true
        );

        for(String header : request.getHeaders()) {
            if (header != null) {
                result.addHeader(header);
            }
        }

        byte[] requestBody = request.getBody();

        if (requestBody != null) {
            result.addHeader("Content-Length: " + requestBody.length);
            result.setBody(requestBody);
        }

        return result;
    }

    public Request toRequest() {
        return toRequest(this.request);
    }

    private int toOneRequestMethod(RequestMethod requestMethod) {
        switch (requestMethod) {
            case GET:
                return Request.METHOD_GET;
            case POST:
                return Request.METHOD_POST;
            case PUT:
                return Request.METHOD_PUT;
            case PATCH:
                return Request.METHOD_PATCH;
            case DELETE:
                return Request.METHOD_DELETE;
            case HEAD:
                return Request.METHOD_HEAD;
            case OPTIONS:
                return Request.METHOD_OPTIONS;
            case TRACE:
                return Request.METHOD_TRACE;
            default:
                throw new IllegalArgumentException("Request method " + requestMethod + " is not supported");
        }
    }
}
