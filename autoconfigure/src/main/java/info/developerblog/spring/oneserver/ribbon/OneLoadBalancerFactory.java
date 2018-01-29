package info.developerblog.spring.oneserver.ribbon;

import java.util.Map;

import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;

/**
 * @author alexander.tarasov
 */
public class OneLoadBalancerFactory {
    private final SpringClientFactory factory;

    private volatile Map<String, OneLoadBalancer> cache = new ConcurrentReferenceHashMap<>();

    public OneLoadBalancerFactory(SpringClientFactory factory) {
        this.factory = factory;
    }

    public OneLoadBalancer create(String clientName) {
        if (this.cache.containsKey(clientName)) {
            return this.cache.get(clientName);
        }
        IClientConfig config = this.factory.getClientConfig(clientName);
        ILoadBalancer lb = this.factory.getLoadBalancer(clientName);
        ServerIntrospector serverIntrospector = this.factory.getInstance(clientName, ServerIntrospector.class);
        OneLoadBalancer client = new OneLoadBalancer(lb, config, serverIntrospector, new OneLoadBalancedHttpClient());
        this.cache.put(clientName, client);
        return client;
    }
}
