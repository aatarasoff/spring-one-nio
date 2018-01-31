package info.developerblog.spring.oneserver.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.client.ClientRequest;
import feign.Client;
import org.apache.commons.lang.StringUtils;

import one.nio.http.HttpClient;
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

        result.setBody(request.getBody());

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
