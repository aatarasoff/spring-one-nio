package info.developerblog.spring.oneserver.client;

import com.netflix.client.ClientException;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancerFactory;
import lombok.extern.slf4j.Slf4j;

import one.nio.http.Response;

/**
 * @author alexander.tarasov
 */
@Slf4j
public class OneHttpClient {
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
