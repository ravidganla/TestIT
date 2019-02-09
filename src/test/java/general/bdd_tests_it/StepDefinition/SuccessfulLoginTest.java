package general.bdd_tests_it.StepDefinition;		

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;		
import cucumber.api.java.en.Then;		
import cucumber.api.java.en.When;		

public class SuccessfulLoginTest {				
	
	private WebDriver driver;
    private Properties testConstants = new Properties();
	private String userLogin;
	private String userPassword;
    
	@Before
    public void beforeScenario(Scenario scenario){

        InputStream stream = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        stream = loader.getResourceAsStream("testConstants.properties");
        
        try {
            testConstants.load(stream);
            userLogin = testConstants.getProperty("UserLogin");
            userPassword = testConstants.getProperty("UserPassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
    
	@After
    public void cleanUp(){
	       driver.quit();
	}	
	
    @Given("^Open the Firefox and launch the application$")				
    public void open_the_Firefox_and_launch_the_application(){		
        System.out.println("This Step open the Firefox and launch the application.");
        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
        driver= new FirefoxDriver();					
        driver.manage().window().maximize();			
        driver.get("https://accounts.google.com/ServiceLogin");
    }		

    @When("^Enter valid Username and Password$")					
    public void enter_the_Username_and_Password(){		
       System.out.println("This step enter the Username and Password on the login page.");
       driver.findElement(By.id("identifierId")).sendKeys(userLogin);					
       driver.findElement(By.xpath("//div/div/div/div/content/span[text()='Next']")).click();	
       new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.name("password"))).click();
       driver.findElement(By.name("password")).sendKeys(userPassword);	
    }		

    @Then("^Click login and navigate$")					
    public void click_login_and_navigate() throws Throwable {    		
	    driver.findElement(By.xpath("//div/div/div/div/content/span[text()='Next']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div/div/div/header/h1")).getText().contains("Welcome"));
        Thread.sleep(1000);
        // Not part of test - just to demo navigation
        driver.findElement(By.xpath("//a/div[2][text()='Personal info']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a/div[2][text()='Data & personalization']")).click();
        Thread.sleep(1000);
    }		
    
    @Then("^Logout of application$")					
    public void logout_of_application(){    		
        driver.findElement(By.xpath("//header/div[2]/div[3]/div/div[2]/div/a/span")).click();
        driver.findElement(By.xpath("//a[text()='Sign out']")).click();
        
	    new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//div/h1"))).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div/h1")).getText().contains("Choose an account"));
    }		
}