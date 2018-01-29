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

import com.netflix.client.ClientRequest;
import feign.Client;

import one.nio.http.HttpClient;
import one.nio.http.Request;

/**
 * @author alexander.tarasov
 */
public class OneHttpRequest extends ClientRequest implements Cloneable {
    private final Request request;

    public OneHttpRequest(Request request, URI uri) {
        setUri(uri);
        this.request = toRequest(request);
    }

    private Request toRequest(Request request) {
        Request result = new Request(
                request.getMethod(),
                getUri().getPath(),
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
}
