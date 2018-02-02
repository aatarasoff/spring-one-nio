package info.developerblog.spring.oneserver.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.client.ClientException;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancerFactory;

/**
 * @author alexander.tarasov
 */
public class OneHttpClient {
    @Autowired
    OneLoadBalancerFactory loadBalancerFactory;

    public OneHttpResponse call(String serviceId, OneHttpRequest oneHttpRequest) {
        try {
            return loadBalancerFactory.create(serviceId).executeWithLoadBalancer(oneHttpRequest);
        } catch (ClientException e) {
            throw new RuntimeException(e.getErrorMessage(), e);
        }
    }
}
