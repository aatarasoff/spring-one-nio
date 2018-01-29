package info.developerblog.spring.oneserver.ribbon;

import java.io.IOException;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

import com.netflix.client.ClientException;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;

import one.nio.http.HttpClient;
import one.nio.http.HttpException;
import one.nio.net.ConnectionString;
import one.nio.pool.PoolException;

/**
 * @author alexander.tarasov
 */
public class OneLoadBalancedHttpClient {

    public OneLoadBalancedHttpClient() {
    }

    public OneHttpResponse execute(OneHttpRequest request) {
        String connectionString = request.getUri().getScheme() + "://" + request.getUri().getHost() + ":" + request.getUri().getPort();
        HttpClient client = new HttpClient(new ConnectionString(connectionString));
        try {
            return new OneHttpResponse(client.invoke(request.toRequest()), request.getUri());
        } catch (InterruptedException | PoolException | IOException | HttpException e) {
            e.printStackTrace();
        }
        return null;
    }
}
