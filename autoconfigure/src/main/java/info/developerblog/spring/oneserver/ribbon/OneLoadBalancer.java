package info.developerblog.spring.oneserver.ribbon;

import java.net.URI;

import org.springframework.cloud.netflix.ribbon.ServerIntrospector;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import static org.springframework.cloud.netflix.ribbon.RibbonUtils.updateToSecureConnectionIfNeeded;

/**
 * @author alexander.tarasov
 */
public class OneLoadBalancer extends AbstractLoadBalancerAwareClient<OneHttpRequest, OneHttpResponse> {
    private IClientConfig clientConfig;
    private ServerIntrospector serverIntrospector;

    private OneLoadBalancedHttpClient oneLoadBalancedHttpClient;

    public OneLoadBalancer(ILoadBalancer lb,
                           IClientConfig clientConfig,
                           ServerIntrospector serverIntrospector,
                           OneLoadBalancedHttpClient oneLoadBalancedHttpClient) {
        super(lb, clientConfig);
        this.clientConfig = clientConfig;
        this.serverIntrospector = serverIntrospector;
        this.oneLoadBalancedHttpClient = oneLoadBalancedHttpClient;
    }

    public OneHttpResponse execute(OneHttpRequest request) throws Exception {
        return execute(request, null);
    }

    @Override
    public OneHttpResponse execute(OneHttpRequest request,
                                   final IClientConfig configOverride) throws Exception {
        IClientConfig config = configOverride != null ? configOverride : this.clientConfig;
        return oneLoadBalancedHttpClient.execute(request);
    }

    @Override
    public URI reconstructURIWithServer(Server server, URI original) {
        URI uri = updateToSecureConnectionIfNeeded(original, this.clientConfig, this.serverIntrospector, server);
        return super.reconstructURIWithServer(server, uri);
    }

    @Override
    public RequestSpecificRetryHandler getRequestSpecificRetryHandler(OneHttpRequest request, IClientConfig requestConfig) {
        return new RequestSpecificRetryHandler(false, false, getRetryHandler(), requestConfig);
    }

}