package info.developerblog.sample;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import info.developerblog.spring.oneserver.client.OneHttpClient;
import info.developerblog.spring.oneserver.client.OneHttpRequest;
import info.developerblog.spring.oneserver.client.OneHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OneDemoApplicationTests {
	@Autowired
	OneHttpClient oneHttpClient;

	@Test
	public void simpleClientCall() throws Exception {
		HttpClient client = new HttpClient(new ConnectionString("http://localhost:10080"));
		Response response = client.get("/simple?name=system");

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("I'm simple system", response.getBodyUtf8());
	}

	@Test
	public void loadBalancedClientCall() throws Exception {
		OneHttpResponse response = oneHttpClient.call(
				"cool-app",
				OneHttpRequest.get("/loop")
		);

		Assert.assertEquals(true, response.isSuccess());
	}

	@Test
	public void loadBalancedPostCall() throws Exception {
		byte[] payload = "Hello!".getBytes(StandardCharsets.UTF_8);

		OneHttpResponse response = oneHttpClient.call(
				"cool-app",
				OneHttpRequest.post("/withBody").withBody(payload)
		);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(Arrays.equals(payload, (byte[]) response.getPayload()));
	}
}
