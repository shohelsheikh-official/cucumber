package glue.stepDefs;

import glue.util.DriverSingleton;
import glue.context.PageContextUI;
import glue.pages.RegisterPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java8.En;
import org.openqa.selenium.WebDriver;

public class Login implements En {

    WebDriver driver;
    String creds;

    public Login(PageContextUI pageContextUI){

        Given("User is Registered", () -> {
            pageContextUI.getRegisterPage().navigateToRegisterPage();
            creds = RegisterPage.getName();
            pageContextUI.getRegisterPage().registerUser(creds);
        });

        Given("User is on Home Page", () -> {
            pageContextUI.getHomePage().navigateToHomePage();
        });

        When("User enters UserName and Password", () -> {
            pageContextUI.getHomePage().clickLoginButton();
        });

        When("click on submit button", () -> {
            // Write code here that turns the phrase above into concrete actions
            pageContextUI.getLoginPage().performLogin(creds);
            Thread.sleep(500);
        });

        Then("User logged in Successfully", () -> {
            pageContextUI.getLoginPage().verifyLogin();
        });
    }

    @Before
    public void setupDriver(){
        driver = DriverSingleton.getInstance().getDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        DriverSingleton.closeDriver();
    }
}
