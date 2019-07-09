package cucumberTestWithSpring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class CucumberPropsConfig {

  @Value("${test.url}")
  private String url;

  @Value("${test.statusCode}")
  private int statusCode;

  public int getStatusCode() {
    return statusCode;
  }

  public String getUrl() {
    return url;
  }
}