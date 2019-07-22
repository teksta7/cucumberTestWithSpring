package com.jake.test;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/"},
		plugin = {"pretty", "json:target/Mock-Service.json"},
		tags = {"@MockTest"})
public class CucumberTest {

}
