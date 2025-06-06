package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    private By emptyCartLocator = By.id("empty_cart");

    public WebElement getEmptyCart(){
      WebElement emptyCart = helpers.Utils.waitForElementPresence(driver, emptyCartLocator, 10);
      return emptyCart;
    }
}
