package info.developerblog.spring.oneserver.ribbon;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;

import one.nio.http.HttpClient;
import one.nio.net.ConnectionString;

/**
 * @author alexander.tarasov
 */
public class OneLoadBalancedHttpClient {
    private ConcurrentMap<String, HttpClient> httpClients = Maps.newConcurrentMap();

    public OneLoadBalancedHttpClient() {
    }

    public OneHttpResponse execute(OneHttpRequest request) {
        String connectionString = request.getUri().getScheme() + "://" + request.getUri().getHost() + ":" + request.getUri().getPort();

        try {
            return new OneHttpResponse(
                    httpClients.computeIfAbsent(
                            connectionString,
                            k -> new HttpClient(new ConnectionString(connectionString))
                    ).invoke(request.toRequest()),
                    request.getUri()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
