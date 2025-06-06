package selenium;

import org.apache.hc.client5.http.impl.ChainElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage {
    WebDriver driver;

    private By searchBarLocator = By.id("search_product");
    private By submit = By.id("submit_search");
    private By searchResultsImages = By.cssSelector("div.productinfo img");
    private By categoryLocator = By.className("category-products");
    private By getFirstBadgeLocator = By.cssSelector(".panel-group .panel:first-of-type .badge.pull-right");
    private By getDressLocator = By.xpath("//a[normalize-space()='Dress']");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    private WebElement getSearchBar() {
        return helpers.Utils.waitForElementPresence(driver, searchBarLocator, 10);
    }

    public WebElement getSubmit() {
        return helpers.Utils.waitForElementPresence(driver, submit, 10);
    }

    public List<WebElement> getSearchImages() {
        return driver.findElements(searchResultsImages);
    }

    public void productSearchFor(String searchText) {
        getSearchBar().clear();
        getSearchBar().sendKeys(searchText);
        getSubmit().click();
    }
    public WebElement getCategory(){
        return helpers.Utils.waitForElementPresence(driver, categoryLocator, 10);
    }
    public WebElement getFirstBadge(){
        return helpers.Utils.waitToBeClickable(driver, getFirstBadgeLocator, 10);
    }
    public WebElement getDress(){
        return helpers.Utils.waitForElementPresence(driver,getDressLocator, 10 );
    }
}
