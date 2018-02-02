package info.developerblog.spring.oneserver.ribbon;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.developerblog.spring.oneserver.client.OneHttpClient;

/**
 * @author alexander.tarasov
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnBean(SpringClientFactory.class)
@ConditionalOnProperty(value = "one.nio.spring.ribbon.enabled", matchIfMissing = true)
@AutoConfigureAfter(RibbonAutoConfiguration.class)
public class OneRibbonAutoConfiguration {
    @Bean
    public OneLoadBalancerFactory oneLoadBalancerFactory(SpringClientFactory springClientFactory) {
        return new OneLoadBalancerFactory(springClientFactory);
    }

    @Bean
    public OneHttpClient oneHttpClient() {
        return new OneHttpClient();
    }
}
