package info.developerblog.spring.oneserver.client;

import com.netflix.client.ClientException;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancedHttpClient;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.nio.http.Response;

/**
 * @author alexander.tarasov
 */
public class OneHttpClient {
    private static final Logger log = LoggerFactory.getLogger(OneLoadBalancedHttpClient.class);
    OneLoadBalancerFactory loadBalancerFactory;

    public OneHttpClient(OneLoadBalancerFactory loadBalancerFactory) {
        this.loadBalancerFactory = loadBalancerFactory;
    }

    public OneHttpResponse call(String serviceId, OneHttpRequest oneHttpRequest) {
        try {
            return loadBalancerFactory.create(serviceId).executeWithLoadBalancer(oneHttpRequest);
        } catch (ClientException e) {
            log.error(e.getErrorMessage(), e);
            return new OneHttpResponse(new Response(Response.INTERNAL_ERROR), oneHttpRequest.getUri());
        }
    }
}
