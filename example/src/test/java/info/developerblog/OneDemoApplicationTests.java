package info.developerblog;

import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancer;
import info.developerblog.spring.oneserver.ribbon.OneLoadBalancerFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

import one.nio.http.HttpClient;
import one.nio.http.Request;
import one.nio.http.Response;
import one.nio.net.ConnectionString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneDemoApplicationTests {
	@Autowired
	OneLoadBalancerFactory loadBalancerFactory;

	@Test
	public void simpleClientCall() throws Exception {
		HttpClient client = new HttpClient(new ConnectionString("http://localhost:10080"));
		Response response = client.get("/simple");

		assertEquals(200, response.getStatus());
		assertEquals("I'am simple", response.getBodyUtf8());
	}

	@Test
	public void loadBalancedClientCall() throws Exception {
		OneHttpResponse response = loadBalancerFactory
				.create("cool-app")
				.executeWithLoadBalancer(new OneHttpRequest(
						RequestMethod.GET,
						"http://cool-app/simple"
				));

		assertEquals(true, response.isSuccess());
	}
}
