package info.developerblog.spring.oneserver.ribbon;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alexander.tarasov
 */
@Configuration
public class OneRibbonClientConfiguration {
    @Bean
    public OneLoadBalancerFactory oneLoadBalancerFactory(SpringClientFactory springClientFactory) {
        return new OneLoadBalancerFactory(springClientFactory);
    }
}
