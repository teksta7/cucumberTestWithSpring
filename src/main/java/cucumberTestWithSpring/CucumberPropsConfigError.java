package cucumberTestWithSpring;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "error")
public class CucumberPropsConfigError {
	@NotBlank
	private String url;
	@Length(max = 3, min = 3)
	private int statusCode;
	
	//CorrelationID example
	@Pattern(regexp = "^[a-z0-9]{2}[a-z]{2,6}$")
	private String correlationId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}
