package glue.util;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSingleton {

    private static WebDriver driver = null;
    private static DriverSingleton instanceDriverSingleton = null;

    private DriverSingleton(){}

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    public static DriverSingleton getInstance() {
        if(instanceDriverSingleton == null){
            instanceDriverSingleton = new DriverSingleton();
        }
        return instanceDriverSingleton;
    }

    public WebDriver getDriver() {
        if(driver == null){
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
          //  driver = null;
        }
    }
}
