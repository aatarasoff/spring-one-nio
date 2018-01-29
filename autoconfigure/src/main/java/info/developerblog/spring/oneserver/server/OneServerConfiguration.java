package info.developerblog.spring.oneserver.server;

import one.nio.http.HttpServer;
import one.nio.http.HttpServerConfig;
import one.nio.server.AcceptorConfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author alexander.tarasov
 */
@Configuration
@EnableConfigurationProperties(OneServerProperties.class)
public class OneServerConfiguration implements ApplicationContextAware {
    ApplicationContext applicationContext;

    @Autowired
    OneServerProperties properties;

    @Bean
    public HttpServer httpServer() throws IOException {
        AcceptorConfig ac = new AcceptorConfig();
        ac.address = properties.getAdvertiseIp();
        ac.port = properties.getPort();

        HttpServerConfig serverConfig = new HttpServerConfig();
        serverConfig.acceptors = new AcceptorConfig[]{ac};

        HttpServer httpServer =  new HttpServer(serverConfig);

        for (String beanName : applicationContext.getBeanNamesForAnnotation(HttpController.class)) {
            httpServer.addRequestHandlers(applicationContext.getBean(beanName));
        }

        return httpServer;
    }

    @Bean
    public OneServerLifecycle oneServerLifecycle(HttpServer httpServer) {
        return new OneServerLifecycle(httpServer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}