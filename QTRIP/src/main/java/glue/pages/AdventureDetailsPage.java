package glue.pages;

import glue.helpers.Interact;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage extends Interact {
    static WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    static WebElement enterGuestName;

    @FindBy(xpath = "//input[@name='date']")
    static WebElement dateSelect;

    @FindBy(xpath = "//input[@name='person']")
    static WebElement enterPersonCount;

    @FindBy(xpath = "//button[text()='Reserve']")
    static WebElement clickOnReserve;

    @FindBy(id = "reserved-banner")
    static WebElement confirmationMessage;


    public AdventureDetailsPage(WebDriver driver) {
        AdventureDetailsPage.driver = driver;
        PageFactory.initElements(driver, this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 40), driver);
    }

    public static Boolean addGuestDetails(String guestName, String date, String count)
            throws InterruptedException {
        try {
            Actions action = new Actions(AdventureDetailsPage.driver);

            action.click(enterGuestName).perform();

            action.sendKeys(enterGuestName, guestName).perform();

            action.sendKeys(dateSelect, date).perform();
            action.click(enterPersonCount).sendKeys(Keys.CONTROL + "a").sendKeys(Keys.DELETE)
                    .sendKeys(count).build().perform();
            action.click(clickOnReserve).perform();

            fluentWait1();

            if (driver.findElement(By.xpath(
                            "(//div[@id='reserved-banner']/a[@href='../reservations/'])")).isDisplayed()) {
              //  BaseTest.capture(AdventureDetailsPage.driver, "confirmation");

                System.out.println("True");
                driver.findElement(By.xpath("(//div[@id='reserved-banner']/a[@href='../reservations/'])")).click();
             //   waitByURL();
            } else {
                System.out.println("false");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}