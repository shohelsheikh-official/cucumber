package glue.context;

import glue.util.DriverSingleton;
import glue.pages.HomePage;
import glue.pages.LoginPage;
import glue.pages.RegisterPage;
import org.openqa.selenium.WebDriver;

public class PageContextUI {
    WebDriver driver;
    HomePage homePage;
    RegisterPage registerPage;
    LoginPage loginPage;
    //public ExtentTest test;

    public PageContextUI(){
        driver = DriverSingleton.getInstance().getDriver();
       // this.test = test;
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    }

    public HomePage getHomePage(){
        return homePage;
    }

    public RegisterPage getRegisterPage() {return registerPage;}

    public LoginPage getLoginPage() {return loginPage;}
}
