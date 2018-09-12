package info.developerblog.spring.oneserver.ribbon;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;

/**
 * @author alexander.tarasov
 */
public class OneLoadBalancedHttpClient {
    private static final Logger log = LoggerFactory.getLogger(OneLoadBalancedHttpClient.class);
    private static final IClientConfigKey<Boolean> KeepAlive = new CommonClientConfigKey<Boolean>("KeepAlive") {};

    private Cache<String, HttpClient> httpClients = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .removalListener(notification -> ((HttpClient) notification.getValue()).close())
            .build();

    private final IClientConfig clientConfig;

    OneLoadBalancedHttpClient(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    OneHttpResponse execute(OneHttpRequest request) {
        int connectTimeout = clientConfig.get(CommonClientConfigKey.ConnectTimeout, 1000);
        int readTimeout = clientConfig.get(CommonClientConfigKey.ReadTimeout, 10000);
        int minPoolSize = clientConfig.get(CommonClientConfigKey.PoolMinThreads, 0);
        int maxPoolSize = clientConfig.get(CommonClientConfigKey.PoolMaxThreads, 50);
        boolean keepAlive = clientConfig.get(KeepAlive, false);

        String connectionString = request.getUri().getScheme() + "://" +
                request.getUri().getHost() + ":" + request.getUri().getPort() +
                "?timeout=" + readTimeout +
                "&connectTimeout=" + connectTimeout +
                "&clientMinPoolSize=" + minPoolSize +
                "&clientMaxPoolSize=" + maxPoolSize +
                "&keepalive=" + keepAlive;

        try {
            return new OneHttpResponse(
                    httpClients.get(
                            connectionString,
                            () -> new HttpClient(new ConnectionString(connectionString))
                    ).invoke(request.toRequest()),
                    request.getUri()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new OneHttpResponse(new Response(Response.INTERNAL_ERROR), request.getUri());
        }
    }
}
