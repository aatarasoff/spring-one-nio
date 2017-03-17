package info.developerblog;

import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneDemoApplicationTests {

	@Test
	public void simpleClientCall() throws Exception {
		HttpClient client = new HttpClient(new ConnectionString("http://localhost:8080"));
		Response response = client.get("/test/simple");

		assertEquals(200, response.getStatus());
		assertEquals("Simple", response.getBodyUtf8());
	}

}
