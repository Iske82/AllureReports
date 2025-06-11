package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.ProductsPage;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestProductsPageMethods {
    WebDriver driver;
    ProductsPage product;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Use "--headless=new" for Chrome 109+
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.automationexercise.com/products");
        product = new ProductsPage(driver);
    }

    @Test
    public void testTitle() {
        assertEquals(driver.getTitle(), product.getTitle());
    }

    @Test
    public void testSearchBar() {
        product.productSearchFor("T shirt");
        assertTrue(product.getSearchImages().size() > 0);
    }

    @Test
    public void testSearchBarJeans() {
        product.productSearchFor("Jeans");
        assertTrue(product.getSearchImages().size() >= 3);
    }

    @Test
    public void testCategory() {
        assertTrue(product.getCategory().isDisplayed());
        WebElement badge = product.getFirstBadge();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", badge);
        badge.click();
        assertTrue(product.getDress().isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
