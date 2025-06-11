package seleniumTests.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage {
    WebDriver driver;

    // Define locators as private class properties
    private By navBarLocator = By.className("navbar-nav");
    private By navListLocator = By.cssSelector("ul.nav.navbar-nav");
    private By menuItemLocator = By.tagName("li");
    private By anchorTag = By.tagName("a");
    private By headerLocator = By.id("header");

    private By categoryLocator = By.className("category-products");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public WebElement getNavBar() {
        WebElement navBar = helpers.Utils.waitForElementPresence(driver, navBarLocator, 10);
        return navBar;
    }

    public WebElement getHeader() {
        WebElement header = helpers.Utils.waitForElementPresence(driver, headerLocator, 10);
        return header;
    }

    public WebElement getCategory() {
        WebElement category = helpers.Utils.waitForElementPresence(driver, categoryLocator, 10);
        return category;
    }

    public List<WebElement> getMenuItems() {
        WebElement navList = helpers.Utils.waitForElementPresence(driver, navListLocator, 10);
        return navList.findElements(menuItemLocator);
    }

    public String getMenuLinkText(int index) {
        WebElement anchor = getMenuItems().get(index).findElement(anchorTag);
        return anchor.getText().trim();
    }

    public String getMenuLinkHref(int index) {
        WebElement anchor = getMenuItems().get(index).findElement(anchorTag);
        return anchor.getAttribute("href");
    }
}
