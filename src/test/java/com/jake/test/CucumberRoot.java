package com.jake.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import cucumberTestWithSpring.CucumberPropsConfig;
import cucumberTestWithSpring.SpringConfiguration;
import io.cucumber.java.After;
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
	
	private HttpClient httpClient;



	@Before
	public void setup() throws Exception {
		
	    System.out.println("Success StatusCode to check = "+ CPC.getStatusCode());
	    System.out.println("Success URL to test = http://" + CPC.getHost() + ":" + CPC.getPort() + CPC.getUrlPath());
	    System.out.println("Error StatusCode to check = "+ CPC.getEstatusCode());
	    System.out.println("Error URL to test = http://" + CPC.getEhost() + ":" + CPC.getEport() + CPC.getEurlPath());
	    
	    
	}

	@When("^the client calls /url$")
	public void the_client_issues_GET_health() throws Throwable {
		httpClient = new DefaultHttpClient();
		String fullUrl = ("http://" + CPC.getHost() + ":" + CPC.getPort() + CPC.getUrlPath());
		HttpGet request = new HttpGet(fullUrl);
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
	
	//================================================================================//
    // 							       Error Code 								      //
	//================================================================================//
	
	@When("^the client calls /badurl$")
	public void the_client_issues_GET_health_Error() throws Throwable {
		httpClient = new DefaultHttpClient();
		String fullUrl = ("http://" + CPC.getEhost() + ":" + CPC.getEport() + CPC.getEurlPath());
		HttpGet request = new HttpGet(fullUrl);
		request.addHeader("Content-Type", "application/json");
	    response = httpClient.execute(request);
		
		String responsePayload = new String(response.getEntity().getContent().readAllBytes(),StandardCharsets.UTF_8);		
		//Converting to JSON object in case a mapping check is needed
		JsonParser jParser = new JsonParser();
		jResponse = jParser.parse(responsePayload);		
		System.out.println(jResponse.toString());		
	}

	@Then("^the client receives error status code of (\\d+)$")
	public void the_client_receives_error_status_code_of(int statusCode) throws Throwable {
		 HttpStatus currentStatusCode = HttpStatus.resolve(response.getStatusLine().getStatusCode());
		 assertThat("status code is incorrect : " +
               jResponse.toString(), currentStatusCode.value(), is(CPC.getEstatusCode()));
	}
	
	@After
	public void tearDown() throws Throwable {
		//Closing all connections
		httpClient.getConnectionManager().shutdown();
				
	}

}
