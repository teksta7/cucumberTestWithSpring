package cucumberTestWithSpring;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class CucumberPropsConfig {

	@NotBlank
	@Value("${test.host}")
	private String host;

	@Value("${test.port}")
	private String port;

	@Value("${test.urlPath}")
	private String urlPath;

	@Length(max = 3, min = 3)
	@Value("${test.statusCode}")
	private int statusCode;
	
	@NotBlank
	@Value("${error.host}")
	private String ehost;


	@Value("${error.port}")
	private String eport;

	@Value("${error.urlPath}")
	private String eurlPath;

	@Length(max = 3, min = 3)
	@Value("${error.statusCode}")
	private int estatusCode;


	// CorrelationId Example
	// @Pattern(regexp "^[a-z0-9]{2}[a-z0-9.-]{2,6}$"
	// private String correlationId;

	public int getStatusCode() {
		return statusCode;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUrlPath() {
		return urlPath;
	}
	
	public String getEhost() {
		return ehost;
	}

	public String getEport() {
		return eport;
	}

	public String getEurlPath() {
		return eurlPath;
	}

	public int getEstatusCode() {
		return estatusCode;
	}

}