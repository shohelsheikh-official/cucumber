package glue.pages;

import glue.Constants.Constants;
import glue.helpers.Interact;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends Interact {

    static WebDriver driver;
    static String City;

    static String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(id = "autocomplete")
    static WebElement searchBox;

    @FindBy(xpath = "//ul/h5")
    static WebElement noCityFound;
    static By noCity = By.xpath("//ul/h5");

    @FindBy(xpath = "//ul/a/li")
    static WebElement cityFound;
    static By foundCity = By.xpath("//ul/a/li");

    public HomePage(WebDriver driver) {
        HomePage.driver = driver;
        // System.out.println();
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        if (!HomePage.driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public void clickLoginButton(){
        driver.findElement(By.partialLinkText("Here")).click();
        waitByVisibility(By.xpath("//h2[text()='Login']"));
    }

    public Boolean verifyHomePage() {
        return HomePage.driver.getCurrentUrl().equals(url);
    }

    public static Boolean searchCity(String city, String value) throws InterruptedException {
        switch (value) {
            case "Valid":
                // String City = Character.toUpperCase(city.charAt(0)) + city.substring(1);
                City = city;
                click(searchBox);
                Thread.sleep(900);
                SendKeys(searchBox, city);
                Thread.sleep(1500);
                verifyCityPresents(city);
                // Wait(cityFound,"Valid City");
                // System.out.println("before verify");
                // System.out.println(adventurePage);
                return AdventurePage.verifyAdventurePage();

            case "Invalid":
                click(searchBox);
                Thread.sleep(900);
                SendKeys(searchBox, city);
                Thread.sleep(1000);
                Wait(noCityFound, "Invalid City");
                return cityNotPresent(noCityFound);

            default:
                return false;
        }
    }

    private static void verifyCityPresents(String city) throws InterruptedException {
        Wait(cityFound, "Valid City");
        WebElement selectCity =
                driver.findElement(By.xpath("//ul/a/li[normalize-space(text())='" + city + "']"));
//        driver.findElement(RelativeLocator.with(By.xpath("")).)
        click(selectCity);
        Thread.sleep(1500);
    }

    private static Boolean cityNotPresent(WebElement noCityfound) {
        // Wait(noCityFound);
        // System.out.println(noCityfound.getText());

        System.out.println(noCityfound.getText() + " Not Present");
        System.out.println(noCityfound.getText().equals(Constants.noCityMessage));

        return (noCityfound.getText().equals(Constants.noCityMessage));
    }

    public static void Wait(WebElement by, String Scenario) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        switch (Scenario) {
            case "Invalid City":
                wait.until(ExpectedConditions.visibilityOfElementLocated(noCity));
                break;

            case "Valid City":
                wait.until(ExpectedConditions.visibilityOfElementLocated(foundCity));
                break;

            default:
                System.out.println("========== Please Provide Valid Scenario ===========");

        }

    }
}