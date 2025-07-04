package seleniumTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import seleniumTests.helpers.Utils;

import java.util.List;

public class ProductsPage {
    WebDriver driver;

    private By searchBarLocator = By.id("search_product");
    private By submit = By.id("submit_search");
    private By searchResultsImages = By.cssSelector("div.productinfo img");
    private By categoryLocator = By.className("category-products");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    private WebElement getSearchBar() {
        return Utils.waitForElementPresence(driver, searchBarLocator, 10);
    }

    public WebElement getSubmit() {
        return Utils.waitForElementPresence(driver, submit, 10);
    }

    public List<WebElement> getSearchImages() {
        return driver.findElements(searchResultsImages);
    }

    public void productSearchFor(String searchText) {
        getSearchBar().clear();
        getSearchBar().sendKeys(searchText);
        getSubmit().click();
    }

    public WebElement getCategory() {
        return Utils.waitForElementPresence(driver, categoryLocator, 10);
    }
}
