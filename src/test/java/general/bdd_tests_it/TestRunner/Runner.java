package general.bdd_tests_it.TestRunner;		

import cucumber.api.CucumberOptions; 
import org.junit.runner.RunWith;		
import cucumber.api.junit.Cucumber;		

@RunWith(Cucumber.class)				
@CucumberOptions(
		features="src\\test\\java\\general\\bdd_tests_it\\Features",
		glue= {"general.bdd_tests_it"},
		plugin= {"html:target/cucumber-html-report"})						
public class Runner 				
{		

}