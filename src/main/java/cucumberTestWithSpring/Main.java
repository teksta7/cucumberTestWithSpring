package cucumberTestWithSpring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

@Autowired
private static ApplicationContext applicationContext;

public static void main(String[] args) {
    applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    CucumberPropsConfig CPC = applicationContext.getBean(CucumberPropsConfig.class);
    System.out.println("StatusCode to check= "+CPC.getStatusCode());
    System.out.println("URL to test= "+CPC.getUrl());
  }
}
