package info.developerblog.spring.oneserver.ribbon;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import lombok.extern.slf4j.Slf4j;

import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;

/**
 * @author alexander.tarasov
 */
@Slf4j
public class OneLoadBalancedHttpClient {
    private final static IClientConfigKey<Integer> ONE_CLIENT_TIMEOUT =
            new CommonClientConfigKey<Integer>("Timeout") {};

    private ConcurrentMap<String, HttpClient> httpClients = Maps.newConcurrentMap();

    private final int readTimeout;
    private final int maxPoolSize;

    public OneLoadBalancedHttpClient(IClientConfig clientConfig) {
        readTimeout = clientConfig.get(CommonClientConfigKey.ReadTimeout, 10000);
        maxPoolSize = clientConfig.get(CommonClientConfigKey.PoolMaxThreads, 50);
    }

    public OneHttpResponse execute(OneHttpRequest request) {
        String connectionString = request.getUri().getScheme() + "://" +
                request.getUri().getHost() + ":" + request.getUri().getPort() +
                "?timeout=" + readTimeout +
                "&clientMaxPoolSize=" + maxPoolSize +
                "&keepalive=false";

        try {
            return new OneHttpResponse(
                    httpClients.computeIfAbsent(
                            connectionString,
                            k -> new HttpClient(new ConnectionString(connectionString))
                    ).invoke(request.toRequest()),
                    request.getUri()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new OneHttpResponse(new Response(Response.INTERNAL_ERROR), request.getUri());
        }
    }
}
