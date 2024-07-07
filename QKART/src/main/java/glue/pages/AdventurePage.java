package glue.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.asserts.Assertion;
import glue.Constants.Constants;
import glue.helpers.Interact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventurePage extends Interact {

    static WebDriver driver;
    String hourstoSelect;
    static String hours;
    @FindBy(xpath = "//select[@name='duration']")
    WebElement dropDownLocatorDuration;

    @FindBy(id = "category-select")
    WebElement dropDownLocatorCategoty;

    @FindBy(xpath = "//div[@class='ms-3'][1]")
    WebElement hoursClear;

    @FindBy(xpath = "(//div[@class='ms-3'])[2]")
    WebElement categoryClear;

    @FindBy(id = "search-adventures")
    static WebElement searchAdventure;

    @FindBy(xpath = "//div[@class='col-6 col-lg-3 mb-4']")
    static WebElement adventureToSelect;

    @FindBy(xpath = "//div[@class='col-6 col-lg-3 mb-4']")
    static By adventureToSelectWait;

    @FindBy(id = "reservation-panel-available")
    static By forms;

    static WebDriverWait wait;

    List<WebElement> adventures = new ArrayList<>();
    List<WebElement> categoriesAdventureList;
    static List<WebElement> allAdventureList = new ArrayList<>();
    static Map<String, Integer> validationHoursFilter = new HashMap<>();
    static Map<String, Integer> validationCategoryFilter = new HashMap<>();
    static Map<String, Integer> validationUnFilteredAdventures = new HashMap<>();

    //static Assertion assertion = new Assertion();

    public AdventurePage(WebDriver driver) {
        this.driver = driver;
        // System.out.println(this.driver);
        PageFactory.initElements(this.driver, this);
    }

    public void navigateToHomePage() {
        if (!this.driver.getCurrentUrl().equals(Constants.adventurePageURL)) {
            driver.get(Constants.adventurePageURL);
        }
    }

    public static Boolean verifyAdventurePage() {
        return AdventurePage.driver.getCurrentUrl().contains(Constants.adventurePageURL);
    }

    public void selectDurationFilters(String Hours) throws InterruptedException {
        // hourstoSelect = hours;
        hours = Hours;
        String[] temp = hours.split(" ", 0);
        hourstoSelect = temp[0];
        // System.out.println(hourstoSelect);
        Select dropdown = new Select(dropDownLocatorDuration);
        dropdown.selectByValue(hourstoSelect);
        Thread.sleep(1000);
        // temp = new String[0];
    }

    public Boolean filterDetails() {
        if (!hourstoSelect.equals("0-2")) {

            adventures = driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']"));
            // count = adventures.size();
            // System.out.println(adventures.size());
        }

        switch (hourstoSelect) {
            case "0-2":
                validationHoursFilter.put(hours, 0);
                return verifyFilterData(HomePage.City);

            case "2-6":
                validationHoursFilter.put(hours, adventures.size());
                // System.out.println("Duration: "+validationHoursFilter.get("2-6"));
                return verifyFilterData(HomePage.City);


            case "6-12":
                validationHoursFilter.put(hours, adventures.size());
                // System.out.println("Duration: "+validationHoursFilter);
                return verifyFilterData(HomePage.City);


            case "12+":
                validationHoursFilter.put(hours, adventures.size());
                return verifyFilterData(HomePage.City);

            default:
                return false;
        }

    }

    public static Boolean verifyFilterData(String city) {

        switch (HomePage.City) {

            case "Bengaluru":
                return validationHoursFilter.get(hours) == 3 ? true : false;

            case "Singapore":
                return validationHoursFilter.get(hours) == 5 ? true : false;

            case "Goa":
                return validationHoursFilter.get(hours) == 3 ? true : false;

            default:
                return false;
        }
    }

    public void selectCategory(String category) {
        Select dropdown = new Select(dropDownLocatorCategoty);
        dropdown.selectByVisibleText(category);

    }

    public Boolean verifyCategoryfilters(String expectedFilteredResult) {
        // System.out.println("expectedFilterCount: "+expectedFilteredResult);
        if (!hourstoSelect.equals("0-2")) {
          //  categoriesAdventureList = SeleniumWrapper.wrap_findElements(driver, By.xpath("//div[@class='col-6 col-lg-3 mb-4']"));
            //driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']"));


            // categoriesAdventureList.size();
            // System.out.println(adventures.size());
        }

        switch (hourstoSelect) {
            case "0-2":
                validationCategoryFilter.put(hours, categoriesAdventureList.size());
                return verifyCategoryFilterData(HomePage.City, expectedFilteredResult);

            case "2-6":
                validationCategoryFilter.put(hours, categoriesAdventureList.size());
                // System.out.println("category: "+validationCategoryFilter.get("2-6"));
                return verifyCategoryFilterData(HomePage.City, expectedFilteredResult);


            case "6-12":
                validationCategoryFilter.put(hours, categoriesAdventureList.size());
                // System.out.println("category: "+validationCategoryFilter);
                return verifyCategoryFilterData(HomePage.City, expectedFilteredResult);


            case "12+":
                validationCategoryFilter.put(hours, categoriesAdventureList.size());
                return verifyCategoryFilterData(HomePage.City, expectedFilteredResult);

            default:
                return false;
        }

    }

    private Boolean verifyCategoryFilterData(String city, String expectedFilter) {
        switch (HomePage.City) {

            case "Bengaluru":
                return validationCategoryFilter.get(hours).equals(Integer.parseInt(expectedFilter))
                        ? true
                        : false;

            case "Singapore":
                return validationCategoryFilter.get(hours).equals(Integer.parseInt(expectedFilter))
                        ? true
                        : false;

            case "Goa":
                return validationCategoryFilter.get(hours).equals(Integer.parseInt(expectedFilter))
                        ? true
                        : false;

            default:
                return false;
        }
    }

    public void clearHourFilter() throws InterruptedException {
     //   SeleniumWrapper.click(hoursClear);

    }

    public void clearCategoryClear() throws InterruptedException {
       // SeleniumWrapper.click(categoryClear);
    }

    public void clearAllFilters() {
        HomePage.click(hoursClear);
        HomePage.click(categoryClear);
    }

    public Boolean allAdventure(String all) {
        if (!hourstoSelect.equals("0-2")) {

            allAdventureList = driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']"));
            // count = adventures.size();
            // System.out.println(adventures.size());
        }

        switch (hourstoSelect) {
            case "0-2":
                validationUnFilteredAdventures.put(hours, allAdventureList.size());
                return verifyAllAdventures(HomePage.City, all);

            case "2-6":
                validationUnFilteredAdventures.put(hours, allAdventureList.size());
                // System.out.println("category: "+validationUnFilteredAdventures.get("2-6"));
                return verifyAllAdventures(HomePage.City, all);


            case "6-12":
                validationUnFilteredAdventures.put(hours, allAdventureList.size());
                // System.out.println("category: "+validationUnFilteredAdventures);
                return verifyAllAdventures(HomePage.City, all);


            case "12+":
                validationUnFilteredAdventures.put(hours, allAdventureList.size());
                return verifyAllAdventures(HomePage.City, all);

            default:
                return false;
        }
    }

    private Boolean verifyAllAdventures(String city, String all) {
        switch (HomePage.City) {

            case "Bengaluru":
                return validationUnFilteredAdventures.get(hours).equals(Integer.parseInt(all))
                        ? true
                        : false;

            case "Singapore":
                return validationUnFilteredAdventures.get(hours).equals(Integer.parseInt(all))
                        ? true
                        : false;

            case "Goa":
                return validationUnFilteredAdventures.get(hours).equals(Integer.parseInt(all))
                        ? true
                        : false;

            default:
                return false;
        }
    }

    public static Boolean searchAdventure(String adventure) throws InterruptedException {
        //searchAdventure.click();
     //   SeleniumWrapper.click(searchAdventure);
        //searchAdventure.sendKeys(adventure);
      //  SeleniumWrapper.wrap_sendKeys(searchAdventure, adventure);
        Thread.sleep(2000);

        click(adventureToSelect);
        fluentWait();
        return verifyAdventureSelection();
    }

    private static boolean verifyAdventureSelection() throws InterruptedException {
        System.out.println(LoginPage.driver.getCurrentUrl());
        Thread.sleep(1500);
        return LoginPage.driver.getCurrentUrl().contains("?adventure");
    }

    public static WebDriver getDriver() {
        return AdventurePage.driver;
    }
}
