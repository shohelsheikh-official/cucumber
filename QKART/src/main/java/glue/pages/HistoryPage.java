
package glue.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import glue.helpers.Interact;

import java.time.Duration;

public class HistoryPage extends Interact {
    static WebDriver driver;

    static WebDriverWait wait = new WebDriverWait(HomePage.driver, Duration.ofSeconds(10));


    @FindBy(xpath = "//tbody/tr/th")
    static
    WebElement transactionID;

    @FindBy(xpath = "//tbody/tr/td[8]//button")
    static
    WebElement cancelButton;

    static String valueTransactionId;

    public HistoryPage(WebDriver driver) {
        HistoryPage.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static Boolean verifyReservationPage() {
        // System.out.println("-->"+HomePage.driver.getCurrentUrl());
        return driver.getCurrentUrl().endsWith("reservations/");

    }

    public static void getTransctionId(){
        valueTransactionId = transactionID.getText();
        System.out.println("Transaction ID: "+valueTransactionId);
    }

    public static Boolean cancelReservation() throws InterruptedException{
        //cancelButton.click();
        //SeleniumWrapper.click(cancelButton);
        waitByInvisibility(By.xpath("//tbody/tr/td[8]//button"));
        return true;
    }

    public static WebDriver getDriver() {
        return HistoryPage.driver;
    }

}
