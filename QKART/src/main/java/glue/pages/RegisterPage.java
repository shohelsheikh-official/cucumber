package glue.pages;


import net.datafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
//import qtriptest.SeleniumWrapper;

public class RegisterPage {

    static WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";
    String registerPageURL = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    String loginPageURL = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";
    public static String createdUsername;


    @FindBy(xpath = "//a[text()='Register']")
    static WebElement registerLink;

    @FindBy(name = "email")
    static WebElement emailAddress;

    @FindBy(name = "password")
    static WebElement password;

    @FindBy(name = "confirmpassword")
    static WebElement confirmPassword;

    @FindBy(xpath = "//button[text()='Register Now']")
    static WebElement registerNowButton;

    private static String fName;
    private static String lName;
    private static String email;
    private static final String Email_Domain = "@example.com";

    public RegisterPage(WebDriver driver) {
        RegisterPage.driver = driver;

        // AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void navigateToRegisterPage(){
        driver.get(registerPageURL);
    }
    public boolean verifyRegisterPage() {
        return RegisterPage.driver.getCurrentUrl().endsWith("/register");

    }

    public static Boolean clickOnRegisterLink() throws InterruptedException {
        // Boolean status;
        //registerLink.click();
       // SeleniumWrapper.click(registerLink);
        // status = wait(registerPageURL);

        return driver.getCurrentUrl().endsWith("register/");
    }

    public static String getNewUserData(){
        Faker faker = new Faker(new Locale("en-us"));
        fName= faker.name().firstName();
        lName= faker.name().lastName();
        email=fName+"."+lName+Email_Domain.toLowerCase();

        return email;
    }

    public void registerUser(String data) {
        emailAddress.sendKeys(data);
        password.sendKeys(data);
        confirmPassword.sendKeys(data);
        registerNowButton.sendKeys(Keys.ENTER);
    }

    public static Boolean verifyRegistrationIsSuccessfull() {
        // Boolean temp =
        // System.out.println(temp);
        return driver.getCurrentUrl().endsWith("/login");
    }

//    public static void registerNowButtonCick(String uname, String pwd) throws InterruptedException {
//        //registerNowButton.click();
//       // SeleniumWrapper.click(registerNowButton);
//        Thread.sleep(2000);
//        try {
//            driver.switchTo().alert().accept();
//            LoginPage.performLogin(uname, pwd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String getName() {
        return getNewUserData();

    }

    public static void main(String[] args) {
        getName();
    }


}
