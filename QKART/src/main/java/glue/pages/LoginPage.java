package glue.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import glue.Constants.Constants;
import glue.helpers.Interact;

import static org.junit.Assert.assertTrue;

public class LoginPage extends Interact {

    static WebDriver driver;
    static String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(name = "email")
    static
    WebElement emailAddress;

    @FindBy(name = "password")
    static
    WebElement password;

    @FindBy(xpath = "//button[text()='Login to QTrip']")
    static
    WebElement login;

    @FindBy(xpath = "//div[text()='Logout']")
    static
    WebElement logOut;

    public LoginPage(WebDriver driver) {
        LoginPage.driver = driver;

        // AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public static void navigateToLoginPage(){
        if (!HomePage.driver.getCurrentUrl().equals(Constants.loginPageURL)) {
            driver.get(Constants.loginPageURL);
        }
    }

    public void performLogin(String data){

        // emailAddress = driver.findElement(By.name("email"));
        // password = driver.findElement(By.name("password"));
        // login = driver.findElement(By.xpath("//button[text()='Login to QTrip']"));
        emailAddress.sendKeys(data);
        password.sendKeys(data);
        login.click();
    }


    public void verifyLogin() throws InterruptedException {
        Thread.sleep(2000);
         assertTrue(this.driver.getCurrentUrl().endsWith(".app/"));
    }

    public static void performLogOut() throws InterruptedException {
       // SeleniumWrapper.click(logOut);;
    }

    public static Boolean verifyLogOut() {
        return LoginPage.driver.getCurrentUrl().equals(url);
    }

    public static WebDriver getDriver(){
        return LoginPage.driver;
    }

}
