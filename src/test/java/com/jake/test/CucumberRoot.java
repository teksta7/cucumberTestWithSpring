package com.jake.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import cucumberTestWithSpring.CucumberPropsConfig;
import cucumberTestWithSpring.SpringConfiguration;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

//@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberRoot {
	
	@Autowired
	private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    CucumberPropsConfig CPC = applicationContext.getBean(CucumberPropsConfig.class);
	
	@Autowired
	protected TestRestTemplate template;
 
	private HttpResponse response; // output
	
	private JsonElement jResponse;		



	@Before
	public void setup() throws Exception {
		
	    System.out.println("StatusCode to check= "+CPC.getStatusCode());
	    System.out.println("URL to test= "+CPC.getUrl());
	    
	}

	@When("^the client calls /url$")
	public void the_client_issues_GET_health() throws Throwable {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:8080" + CPC.getUrl());
		request.addHeader("Content-Type", "application/json");
	    response = httpClient.execute(request);
		
		String responsePayload = new String(response.getEntity().getContent().readAllBytes(),StandardCharsets.UTF_8);		
		//Converting to JSON object in case a mapping check is needed
		JsonParser jParser = new JsonParser();
		jResponse = jParser.parse(responsePayload);		
		System.out.println(jResponse.toString());
		
	}

	@Then("^the client receives response status code of (\\d+)$")
	public void the_client_receives_status_code_of(int statusCode) throws Throwable {
		 HttpStatus currentStatusCode = HttpStatus.resolve(response.getStatusLine().getStatusCode());
		 assertThat("status code is incorrect : " +
               jResponse.toString(), currentStatusCode.value(), is(CPC.getStatusCode()));
	}

}

//    @Autowired
//    protected TestRestTemplate template;
//    
//    private ResponseEntity<String> response; // output
//
//    @Before
//    public void before() {
//    	
//        // demo to show how to add custom header Globally for the http request in spring test template , like  user header
////        template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
////            request.getHeaders()
////                    .add("userHeader", "user");
////            return execution.execute(request, body);
////        }));
//        template.getRestTemplate().setRequestFactory(new ClientHttpRequestFactory() {
//			
//			public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
//    }
//    
//    @When("^the client calls /version$")
//    public void the_client_issues_GET_health() throws Throwable {
//        response =  template.getForEntity("/health", String.class);
//    }
//    @Then("^the client receives response status code of (\\d+)$")
//    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
//        HttpStatus currentStatusCode = response.getStatusCode();
//        assertThat("status code is incorrect : " +
//                response.getBody(), currentStatusCode.value(), is(statusCode));
//    }
//
//}