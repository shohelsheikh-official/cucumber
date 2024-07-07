package glue.helpers;

import glue.util.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Interact {
    static WebDriver driver = DriverSingleton.getInstance().getDriver();

    static WebDriverWait wait;

    public Interact(WebDriver driver) {
        Interact.driver = driver;
        wait = new WebDriverWait(Interact.driver, Duration.ofSeconds(20));
    }

    public Interact() {}

    public static void waitTillElementToBeClicked(WebElement elementToWait) {
        wait.until(ExpectedConditions.elementToBeClickable(elementToWait));
    }

    public static void waitTillPresenceOfElement(WebElement elementToWait) {
        wait.until(ExpectedConditions.elementToBeClickable(elementToWait));
    }

    public static void fluentWait1() {
        FluentWait<WebDriver> wait =
                new FluentWait<WebDriver>(driver).withTimeout((Duration.ofSeconds(30)))
                        .pollingEvery(Duration.ofMillis(250)).ignoring(Exception.class);

        wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//a[@href='../reservations/']")));
    }

    public static void fluentWait() {
        FluentWait<WebDriver> wait =
                new FluentWait<WebDriver>(driver).withTimeout((Duration.ofSeconds(30)))
                        .pollingEvery(Duration.ofMillis(250)).ignoring(Exception.class);

        wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.className("reserve-button")));
    }

//    public static void waitByURL(){
//        wait.until(ExpectedConditions.urlToBe(Constants.reservationURLOnClick));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr/th")));
//    }

    public static void waitByInvisibility(By by){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    public static void waitByVisibility(By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));}

    public static void click(WebElement elementToBeClicked) {
        elementToBeClicked.click();
    }

    public static void SendKeys(WebElement textField, String textToEnter) {
        textField.sendKeys(textToEnter);
    }

    public static Boolean wait(String urlToCheck) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe(urlToCheck));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
