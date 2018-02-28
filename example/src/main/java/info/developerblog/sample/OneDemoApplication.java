package info.developerblog.sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OneDemoApplication {
	public static void main(String[] args) {
        new SpringApplicationBuilder(OneDemoApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
	}
}
